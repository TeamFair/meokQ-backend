package com.meokq.api.challenge.repository.queryDSL

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ReadChallengeRespForQueryDSL
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.user.model.QCustomer.customer
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ChallengeQueryDSLRepositoryImpl: Querydsl4RepositorySupport(Challenge::class.java) {

    fun findAll(searchDto: ChallengeSearchDto, pageable: Pageable): Page<ReadChallengeRespForQueryDSL> {

        return applyPagination(pageable, { contentQuery ->
            contentQuery
                .select(
                    Projections.constructor(
                    ReadChallengeRespForQueryDSL::class.java, challenge,customer, mission.content, quest))
                .from(challenge)
                .leftJoin(quest).on(challenge.questId.eq(quest.questId))
                .leftJoin(mission).on(challenge.questId.eq(mission.questId))
                .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
                .where(
                    questIdEq(searchDto.questId),
                    userIdEq(searchDto.userId),
                    statusEq(searchDto.status),
                )
        },{countQuery->
            countQuery
                .select(challenge.count())
                .from(challenge)
                .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
                .where(
                    questIdEq(searchDto.questId),
                    userIdEq(searchDto.userId),
                    statusEq(searchDto.status),
                )
        })

    }
    private fun questIdEq(questId : String?): BooleanExpression? {
        return if (questId.isNullOrBlank()) null else challenge.questId.eq(questId)
    }
    private fun userIdEq(userId : String?): BooleanExpression? {
        return if (userId.isNullOrBlank()) null else challenge.customerId.eq(userId)
    }
    private fun statusEq(status: ChallengeStatus?): BooleanExpression? {
        return status?.let { challenge.status.eq(it) }
    }
}