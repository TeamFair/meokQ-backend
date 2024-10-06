package com.meokq.api.user.repository.queryDSL

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.model.QChallenge
import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.user.model.Customer
import com.meokq.api.user.model.QCustomer.customer
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.response.XpRankCustomerResp
import com.meokq.api.xp.model.QXp.xp
import com.meokq.api.xp.model.XpType
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class CustomerQueryDSLRepository : Querydsl4RepositorySupport(Customer::class.java) {

    fun getXpRanking(con: RankSearchCondition): List<XpRankCustomerResp> {
        return queryFactory.select(
            Projections.constructor(
                XpRankCustomerResp::class.java, customer, xp.xpType, xp.xpPoint.max())  // MAX로 xpPoint 처리
        )
            .from(customer)
            .leftJoin(customer.xp, xp)
            .where(
                xp.xpType.eq(con.xpType))
            .groupBy(xp.xpType, customer)
            .orderBy(xp.xpPoint.max().desc())  // MAX로 집계된 xpPoint의 내림차순 정렬
            .limit(con.size.toLong())
            .fetch()
    }




}