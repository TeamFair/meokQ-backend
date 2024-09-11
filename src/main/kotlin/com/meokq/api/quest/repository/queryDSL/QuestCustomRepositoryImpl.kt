package com.meokq.api.quest.repository.queryDSL

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.quest.model.QReward.reward
import com.meokq.api.quest.model.Quest
import com.querydsl.jpa.JPAExpressions
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
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

}