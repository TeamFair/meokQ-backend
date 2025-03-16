package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.challenge.request.ChallengeQuizSearchDto
import com.meokq.api.challenge.response.ReadChallengeQuizResp
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.user.model.QCustomer.customer
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class ChallengeQuizDslImpl(
    private val queryFactory: JPAQueryFactory
) : ChallengeQuizDsl {
    override fun getChallengeQuizList(
        dto: ChallengeQuizSearchDto,
        userId: String?,
        pageable: PageRequest
    ): PageImpl<ReadChallengeQuizResp> {

        val query = queryFactory
            .select(
                Projections.constructor(
                    ReadChallengeQuizResp::class.java,
                    challenge,
                    customer,
                    mission.content,
                    quest
                )
            )
            .from(challenge)
            .leftJoin(quest).on(challenge.questId.eq(quest.questId))  // fetchJoin 제거
            .leftJoin(mission).on(mission.questId.eq(challenge.questId))
            .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
            .where(
                userIdEq(userId),
                statusEq(dto.status),
                questIdEq(dto.questId),  // questIdEq에서 quest.questId 직접 참조 안 함
                questTypeEq(dto.questType),
                missionTypeIn(MissionType.WORDS, MissionType.OX)
            )
            .orderBy(challenge.createDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val results = query.fetch()
        val total = queryFactory.select(challenge.count())  // count 쿼리 최적화
            .from(challenge)
            .leftJoin(quest).on(challenge.questId.eq(quest.questId))
            .leftJoin(mission).on(mission.questId.eq(challenge.questId))
            .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
            .where(
                userIdEq(userId),
                statusEq(dto.status),
                questIdEq(dto.questId),
                questTypeEq(dto.questType),
                missionTypeIn(MissionType.WORDS, MissionType.OX)
            )
            .fetchOne() ?: 0

        return PageImpl(results, pageable, total)
    }

    private fun userIdEq(userId: String?): BooleanExpression? {
        return userId?.let { challenge.customerId.eq(it) }
    }

    private fun statusEq(status: ChallengeStatus?): BooleanExpression? {
        return status?.let { challenge.status.eq(status) }
    }

    private fun questIdEq(questId: String?): BooleanExpression? {
        return questId?.let { challenge.questId.eq(it) } // quest 참조 제거
    }

    private fun questTypeEq(type: QuestType?): BooleanExpression? {
        return type?.let { quest.type.eq(it) }
    }

    private fun missionTypeIn(vararg types: MissionType): BooleanExpression {
        return mission.type.`in`(*types)
    }
}