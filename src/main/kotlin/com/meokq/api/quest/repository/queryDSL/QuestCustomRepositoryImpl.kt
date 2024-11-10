package com.meokq.api.quest.repository.queryDSL

import com.meokq.api.auth.enums.UserType
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.MissionTarget
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.quest.model.QReward.reward
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestQueryDSLListResp
import com.meokq.api.quest.response.RewardResp
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.nullExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Repository
@Transactional(readOnly = true)
class QuestCustomRepositoryImpl: Querydsl4RepositorySupport(Quest::class.java) {
    /**
     * 완료된 퀘스트 목록 조회
     */

    fun getCompletedQuests(pageable: Pageable, userId: String): Page<QuestQueryDSLListResp> {
        val resultPage: Page<QuestQueryDSLListResp> = applyPagination(
            pageable,
            { queryFactory ->
                queryFactory
                    .select(createQuestProjection())
                    .from(challenge)
                    .join(quest).on(challenge.questId.eq(quest.questId))
                    .join(mission).on(quest.questId.eq(mission.questId))
                    .where(
                        challenge.customerId.eq(userId)
                            .and(challenge.status.eq(ChallengeStatus.APPROVED))
                            .and(quest.status.eq(QuestStatus.PUBLISHED))
                    )
                    .orderBy(quest.createDate.desc()) // 필요에 따라 정렬 추가
            },
            { queryFactory ->
                queryFactory
                    .select(quest.count())
                    .from(challenge)
                    .join(quest).on(challenge.questId.eq(quest.questId))
                    .where(
                        challenge.customerId.eq(userId)
                            .and(challenge.status.eq(ChallengeStatus.APPROVED))
                            .and(quest.status.eq(QuestStatus.PUBLISHED))
                    )
            }
        )

        val rewards = queryFactory.select(
            Projections.constructor(
                RewardResp::class.java,
                reward.rewardId,
                reward.content,
                reward.target,
                reward.quantity,
                reward.discountRate,
                reward.type,
                nullExpression(String::class.java),
                reward.questId
            )
        )
            .from(reward)
            .where(reward.questId.`in`(resultPage.content.map { it.questId }))
            .fetch()

        val questMap = rewards.groupBy { it.questId }

        return resultPage.map { quest ->
            val rewardList = questMap[quest.questId]?.toMutableList() ?: mutableListOf()
            quest.addRewardList(rewardList)
            quest
        }

    }


    /**
     * 미완료된 퀘스트 목록 조회
     */
    fun getUnCompletedQuests(pageable: Pageable, userId: String): Page<QuestQueryDSLListResp> {
        val questIdsSubQuery = JPAExpressions
            .select(challenge.questId)
            .from(challenge)
            .where(
                challenge.customerId.eq(userId)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED)),
             )

        val questIdCondition: BooleanExpression = quest.questId.notIn(questIdsSubQuery)

        val additionalConditions = listOf(questIdCondition)

        val orderSpecifiers = sortGenerator(pageable)

        return fetchQuests(
            pageable = pageable,
            dynamicCond = listOf(
                quest.status.eq(QuestStatus.PUBLISHED)
            ) + additionalConditions,
            orderCond = orderSpecifiers
        )
    }

    /**
     * 주기별 반복 퀘스트 목록 조회
     */
    fun getUncompletedRepeatableQuests(
        pageable: Pageable,
        userId: String,
        missionTarget: MissionTarget
    ): Page<QuestQueryDSLListResp> {
        val today = LocalDateTime.now()
        val startDate: LocalDateTime = when (missionTarget) {
            MissionTarget.DAILY -> today.minusDays(1)
            MissionTarget.WEEKLY -> today.minusDays(7)
            MissionTarget.MONTHLY -> today.withDayOfMonth(1)
            else -> throw IllegalArgumentException("Invalid quest type for repeatable quests.")
        }

        val completedQuestIdsSubQuery = JPAExpressions
            .select(challenge.questId)
            .from(challenge)
            .where(
                challenge.customerId.eq(userId)
                    .and(challenge.createDate.goe(startDate))
            )

        val dynamicCond = listOf(
            mission.target.eq(missionTarget),
            quest.status.eq(QuestStatus.PUBLISHED),
            mission.type.eq(MissionType.REPEAT),
            quest.questId.notIn(completedQuestIdsSubQuery)
        )

        val orderCond = sortGenerator(pageable)


        val rewards = queryFactory.select(
            Projections.constructor(
                RewardResp::class.java,
                reward.rewardId,
                reward.content,
                reward.target,
                reward.quantity,
                reward.discountRate,
                reward.type,
                nullExpression(String::class.java),
                reward.questId
            )
        )
            .from(reward)
            .fetch()


        return applyPagination(
            pageable,
            { queryFactory ->
                queryFactory.select(createQuestProjection())
                    .from(quest)
                    .where(*dynamicCond.toTypedArray())
                    .leftJoin(quest.missions, mission)
                    .orderBy(*orderCond.toTypedArray())
            },
            { queryFactory ->
                queryFactory.select(quest.count())
                    .from(quest)
                    .leftJoin(quest.missions, mission)
                    .where(*dynamicCond.toTypedArray())
            }
        )
    }


    /**
     * 퀘스트 검색 기능 추가
     */
    fun findAll(searchReq: QuestSearchDto, pageable: Pageable): Page<QuestQueryDSLListResp> {
        // 조건 설정
        val dynamicConditions = listOfNotNull(
            statusEq(searchReq.status),
            marketIdEq(searchReq.marketId),
            questIdEq(searchReq.questId),
            creatorRoleEq(searchReq.creatorRole)
        )

        // 공통 쿼리 실행
        return fetchQuests(
            pageable = pageable,
            dynamicCond = dynamicConditions,
            orderCond = sortGenerator(pageable)
        )
    }

    /**
     * 공통 쿼리 실행 및 리워드 매핑 로직
     */
    private fun fetchQuests(pageable: Pageable, dynamicCond: List<BooleanExpression>, orderCond: List<OrderSpecifier<*>>): Page<QuestQueryDSLListResp> {
        // 정렬 조건 생성

        // 콘텐츠 쿼리 정의
        val contentQuery: (JPAQueryFactory) -> JPAQuery<QuestQueryDSLListResp> = { queryFactory ->
            queryFactory.select(createQuestProjection())
                .from(quest)
                .leftJoin(quest.missions, mission)
                .where(*dynamicCond.toTypedArray())
                .orderBy(*orderCond.toTypedArray())
        }

        // 카운트 쿼리 정의
        val countQuery: (JPAQueryFactory) -> JPAQuery<Long> = { queryFactory ->
            queryFactory.select(quest.count())
                .from(quest)
                .where(*dynamicCond.toTypedArray())
        }

        // 리워드 데이터 조회 및 매핑
        val rewards = queryFactory.select(
            Projections.constructor(
                RewardResp::class.java,
                reward.rewardId,
                reward.content,
                reward.target,
                reward.quantity,
                reward.discountRate,
                reward.type,
                nullExpression(String::class.java),
                reward.questId
            )
        )
            .from(reward)
            .fetch()

        val questMap = rewards.groupBy { it.questId }

        // 페이징 결과
        val resultPage: Page<QuestQueryDSLListResp> = applyPagination(pageable, contentQuery, countQuery)

        // 리워드 매핑
        return resultPage.map { quest ->
            val rewardList = questMap[quest.questId]?.toMutableList() ?: mutableListOf()
            quest.addRewardList(rewardList)
            quest
        }
    }

    /**
     * 공통 프로젝션 생성 함수
     */
    private fun createQuestProjection(): ConstructorExpression<QuestQueryDSLListResp> {
        return Projections.constructor(
            QuestQueryDSLListResp::class.java,
            quest,
            mission
        )
    }

    /**
     * 정렬 조건 생성 함수
     */
    private fun sortGenerator(pageable: Pageable): List<OrderSpecifier<*>> {
        val orderSpecifiers = mutableListOf<OrderSpecifier<*>>()

        // 유저가 설정한 정렬 옵션을 기반으로 정렬 조건 추가
        val sortFields = pageable.sort
        if (sortFields.isSorted) {
            for (order in sortFields) {
                val orderSpecifier = when (order.property) {
                    "score" -> if (order.isAscending) quest.score.asc() else quest.score.desc()
                    "createDate" -> if (order.isAscending) quest.createDate.asc() else quest.createDate.desc()
                    // 필요에 따라 추가적인 정렬 필드를 여기에 정의할 수 있습니다.
                    else -> null
                }
                orderSpecifier?.let { orderSpecifiers.add(it) }
            }
        }

        // 기본 정렬 조건 (여기에서는 필요할 경우 추가 가능)
        if (orderSpecifiers.isEmpty()) {
            orderSpecifiers.add(quest.createDate.desc())
        }

        return orderSpecifiers
    }

    private fun marketIdEq(marketId : String?): BooleanExpression? {
        return if (marketId.isNullOrBlank()) null else quest.marketId.eq(marketId)
    }

    private fun questIdEq(questId : String?): BooleanExpression? {
        return if (questId.isNullOrBlank()) null else quest.questId.eq(questId)
    }

    private fun statusEq(status: QuestStatus?): BooleanExpression? {
        return status?.let { quest.status.eq(it) }
    }
    private fun creatorRoleEq(creatorRole : UserType?): BooleanExpression? {
        return creatorRole?.let { quest.creatorRole.eq(it) }
    }


}