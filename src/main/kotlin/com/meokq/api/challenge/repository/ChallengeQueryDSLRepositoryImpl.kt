package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.model.QChallenge.challenge
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ReadChallengeResp
import com.meokq.api.challenge.response.ReadChallengeRespForQueryDSL
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.QMission
import com.meokq.api.quest.model.QMission.mission
import com.meokq.api.quest.model.QQuest.quest
import com.meokq.api.title.model.QTitle.title
import com.meokq.api.title.model.QTitleHistory.titleHistory
import com.meokq.api.user.model.QCustomer.customer
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.hibernate.query.criteria.JpaExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ChallengeQueryDSLRepositoryImpl : Querydsl4RepositorySupport(Challenge::class.java) {

    fun findAll(searchDto: ChallengeSearchDto, pageable: Pageable): Page<ReadChallengeRespForQueryDSL> {
        // 서브쿼리 정의
        val queryFirstMission = JPAExpressions
            .select(mission.missionId)
            .from(mission)
            .where(mission.questId.eq(challenge.questId))
            .orderBy(mission.createDate.desc())
            .limit(1)

        return applyPagination(pageable, { contentQuery ->
            contentQuery
                .select(
                    Projections.constructor(
                        ReadChallengeRespForQueryDSL::class.java,
                        challenge,
                        customer,
                        mission.content, // Join된 mission.content
                        quest
                    )
                )
                .from(challenge)
                .leftJoin(quest).on(challenge.questId.eq(quest.questId))
                .leftJoin(mission).on(mission.missionId.eq(queryFirstMission)) // 서브쿼리를 ON 조건에서 사용
                .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
                .orderBy(challenge.createDate.desc())
                .where(
                    questIdEq(searchDto.questId),
                    userIdEq(searchDto.userId),
                    statusEq(searchDto.status)
                )
        }, { countQuery ->
            countQuery
                .select(challenge.count())
                .from(challenge)
                .where(
                    questIdEq(searchDto.questId),
                    userIdEq(searchDto.userId),
                    statusEq(searchDto.status)
                )
        })
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

    private fun questIdEq(questId: String?): BooleanExpression? {
        return if (questId.isNullOrBlank()) null else challenge.questId.eq(questId)
    }

    private fun userIdEq(userId: String?): BooleanExpression? {
        return if (userId.isNullOrBlank()) null else challenge.customerId.eq(userId)
    }

    private fun statusEq(status: ChallengeStatus?): BooleanExpression? {
        return status?.let { challenge.status.eq(it) }
    }

    fun findRandomAllChallenges(pageable: Pageable): Page<ReadChallengeResp> {
        val subMission = QMission("subMission")
        val query = queryFactory
            .select(
                Projections.constructor(
                    ReadChallengeResp::class.java,
                    challenge,
                    customer.nickname,
                    customer.profileImageId,
                    mission,
                    title,
                )
            )
            .from(challenge)
            .leftJoin(customer).on(challenge.customerId.eq(customer.customerId))
            .leftJoin(titleHistory).on(customer.titleHistoryId.eq(titleHistory.id))
            .leftJoin(title).on(title.id.eq(titleHistory.titleId))
            .leftJoin(mission).on(mission.questId.eq(challenge.questId))
            .where(
                JPAExpressions
                    .select(subMission.count())
                    .from(subMission)
                    .where(
                        subMission.questId.eq(challenge.questId)
                            .and(subMission.type.ne(MissionType.FREE))
                    )
                    .eq(0L)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED))
                    .and(
                        JPAExpressions
                            .select(subMission.missionId.max())
                            .from(subMission)
                            .where(subMission.questId.eq(challenge.questId))
                            .eq(mission.missionId)
                    )
            )
            .orderBy(
                Expressions.cases()
                    .`when`(challenge.likeEmojiCnt.eq(0)).then(0)
                    .otherwise(1).desc(),
                challenge.updateDate.desc()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val content = query.fetch()

        val countQuery = queryFactory
            .select(challenge.count())
            .from(challenge)
            .where(
                JPAExpressions
                    .select(mission.count())
                    .from(mission)
                    .where(
                        mission.questId.eq(challenge.questId)
                            .and(mission.type.ne(MissionType.FREE))
                    )
                    .eq(0L)
                    .and(challenge.status.eq(ChallengeStatus.APPROVED))
            )

        val total = countQuery.fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }
}
