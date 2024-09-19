package com.meokq.api.quest.repository.queryDSL

import com.meokq.api.auth.enums.UserType
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.enums.QuestSortOperation
import com.meokq.api.quest.enums.QuestSortOperation.*
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.quest.model.QReward.reward
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QQuestQueryDSLListResp
import com.meokq.api.quest.response.QuestQueryDSLListResp
import com.meokq.api.quest.response.RewardResp
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.nullExpression
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class QuestCustomRepositoryImpl: Querydsl4RepositorySupport(Quest::class.java) {
    /**
     * 완료된 퀘스트 목록 조회
     */
    fun getCompletedQuests(pageable: Pageable, userId: String): Page<QuestQueryDSLListResp> {
        // 서브쿼리 생성
        val questIdsSubQuery = JPAExpressions
            .select(challenge.questId)
            .from(challenge)
            .where(
                challenge.customerId.eq(userId)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED))
            )

        // 완료 여부 조건 설정
        val questIdCondition: BooleanExpression = quest.questId.`in`(questIdsSubQuery)

        // 추가 조건: 퀘스트가 완료된 상태
        val additionalConditions = listOf(questIdCondition)

        // 공통 쿼리 실행
        return fetchQuests(
            pageable = pageable,
            baseConditions = listOf(
                quest.status.eq(QuestStatus.PUBLISHED)
            ) + additionalConditions
        )
    }

    /**
     * 미완료된 퀘스트 목록 조회
     */
    fun getUnCompletedQuests(pageable: Pageable, userId: String): Page<QuestQueryDSLListResp> {
        // 서브쿼리 생성
        val questIdsSubQuery = JPAExpressions
            .select(challenge.questId)
            .from(challenge)
            .where(
                challenge.customerId.eq(userId)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED))
            )

        // 완료 여부 조건 설정
        val questIdCondition: BooleanExpression = quest.questId.notIn(questIdsSubQuery)

        // 추가 조건: 퀘스트가 미완료된 상태
        val additionalConditions = listOf(questIdCondition)

        // 공통 쿼리 실행
        return fetchQuests(
            pageable = pageable,
            baseConditions = listOf(
                quest.status.eq(QuestStatus.PUBLISHED)
            ) + additionalConditions
        )
    }

    /**
     * 퀘스트 검색 기능 추가
     */
    fun findAll(searchReq: QuestSearchDto, pageable: Pageable): Page<QuestQueryDSLListResp> {
        // 조건 설정
        val dynamicConditions = listOf(
            statusEq(searchReq.status),
            marketIdEq(searchReq.marketId),
            questIdEq(searchReq.questId),
            creatorRoleEq(searchReq.creatorRole)
        ).filterNotNull()

        // 공통 쿼리 실행
        return fetchQuests(
            pageable = pageable,
            baseConditions = listOf(
                quest.status.eq(QuestStatus.PUBLISHED)
            ) + dynamicConditions
        )
    }

    /**
     * 공통 쿼리 실행 및 리워드 매핑 로직
     */
    private fun fetchQuests(pageable: Pageable, baseConditions: List<BooleanExpression>): Page<QuestQueryDSLListResp> {
        // 정렬 조건 생성
        val orderSpecifiers = sortGenerator(pageable)

        // 콘텐츠 쿼리 정의
        val contentQuery: (JPAQueryFactory) -> JPAQuery<QuestQueryDSLListResp> = { queryFactory ->
            queryFactory.select(createQuestProjection())
                .from(quest)
                .leftJoin(quest.missions, mission)
                .where(*baseConditions.toTypedArray())
                .orderBy(*orderSpecifiers.toTypedArray())
        }

        // 카운트 쿼리 정의
        val countQuery: (JPAQueryFactory) -> JPAQuery<Long> = { queryFactory ->
            queryFactory.select(quest.count())
                .from(quest)
                .where(*baseConditions.toTypedArray())
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

    private fun sortGenerator(pageable: Pageable): List<OrderSpecifier<*>> {
        val orderSpecifiers = mutableListOf<OrderSpecifier<*>>()
        val sortFields = pageable.sort

        if (sortFields.isSorted) {
            //정렬조건 추가시 아래 코드 사용
            /*for (order in sortFields) {
                val sortField = QuestSortOperation.valueOf(order.property)

                val path = when (sortField) {

                }
                val orderSpecifier = OrderSpecifier(Order.DESC, path)
                orderSpecifiers.add(orderSpecifier)
            }*/

            /* 퀘스트 조회 (정렬 기능 추가 시)
            1순위) 유저가 선택한 정렬기준(인기순, 스탯순 등…)
            2순위) score기준
            3순위) 생성일자 기준 */
            orderSpecifiers.add(quest.score.desc())
            orderSpecifiers.add(quest.createDate.desc())
        } else {
            // 기본 정렬 조건 (생성일 내림차순, 스코어 내림차순)
            orderSpecifiers.add(quest.score.desc())
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