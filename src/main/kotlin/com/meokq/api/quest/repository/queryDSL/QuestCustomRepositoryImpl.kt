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

    fun getCompletedQuests(pageable: Pageable, userId:String): Page<Quest> {
        val challengeExistsQuery = JPAExpressions
            .selectFrom(challenge)
            .where(challenge.questId.eq(quest.questId)
                .and(challenge.customerId.eq(userId))
                .and(challenge.status.eq(ChallengeStatus.APPROVED))
            ).exists()

        return applyPagination(pageable,
            { contentQuery ->
                contentQuery.select(quest)
                    .from(quest)
                    .leftJoin(quest.missions, mission)
                    .leftJoin(quest.rewards, reward).fetchJoin()
                    .where(
                        quest.status.eq(QuestStatus.PUBLISHED),
                        challengeExistsQuery
                    )
            },
            { countQuery ->
                countQuery.select(quest.questId)
                    .from(quest)
                    .where(
                        quest.status.eq(QuestStatus.PUBLISHED),
                        challengeExistsQuery
                    )
            }
        )


    }


}