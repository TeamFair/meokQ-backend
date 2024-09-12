package com.meokq.api.quest.repository.queryDSL

import com.meokq.api.auth.enums.UserType
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.quest.model.QReward.reward
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestListResp
import com.meokq.api.quest.response.QuestQueryDSLListResp
import com.meokq.api.quest.response.RewardResp
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.NullExpression
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

    fun getCompletedQuests(pageable: Pageable, userId: String): Page<Quest> {
        return getQuests(pageable, userId, true)
    }

    fun getUnCompletedQuests(pageable: Pageable, userId: String): Page<Quest> {
        return getQuests(pageable, userId, false)
    }

    private fun getQuests(pageable: Pageable, userId: String, isCompleted: Boolean): Page<Quest> {
        val questIds = JPAExpressions
            .select(challenge.questId)
            .from(challenge)
            .where(
                challenge.customerId.eq(userId)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED))
            )

        val questIdCondition = if (isCompleted) {
            quest.questId.`in`(questIds)
        } else {
            quest.questId.notIn(questIds)
        }

        return applyPagination(pageable,
            { contentQuery ->
                contentQuery.select(quest)
                    .from(quest)
                    .leftJoin(quest.missions, mission)
                    .leftJoin(quest.rewards, reward).fetchJoin()
                    .where(
                        quest.status.eq(QuestStatus.PUBLISHED),
                        questIdCondition
                    )
            },
            { countQuery ->
                countQuery.select(quest.questId.count())
                    .from(quest)
                    .where(
                        quest.status.eq(QuestStatus.PUBLISHED),
                        questIdCondition
                    )
            }
        )
    }

    fun findAll(searchReq: QuestSearchDto, pageable: Pageable): Page<QuestQueryDSLListResp> {

        val contentQuery: (JPAQueryFactory) -> JPAQuery<QuestQueryDSLListResp> = { queryFactory ->
            val questListQuery = queryFactory.select(
                Projections.constructor(
                    QuestQueryDSLListResp::class.java,
                    quest, mission
                )
            )
                .from(quest)
                .leftJoin(quest.missions, mission).fetchJoin()
                .where(statusEq(searchReq.status))

            questListQuery
        }

        val countQuery: (JPAQueryFactory) -> JPAQuery<Long> = { queryFactory ->
            queryFactory.select(quest.count())
                .from(quest)
                .where(statusEq(searchReq.status))
        }

        // 결과 처리 (리워드 리스트 추가)
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

        val resultPage: Page<QuestQueryDSLListResp> = applyPagination(pageable, contentQuery, countQuery)

        val finalResult = resultPage.map { quest ->
            val rewardList = questMap[quest.questId]?.toMutableList() ?: mutableListOf()
            quest.addRewardList(rewardList)
            quest
        }

        return finalResult
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