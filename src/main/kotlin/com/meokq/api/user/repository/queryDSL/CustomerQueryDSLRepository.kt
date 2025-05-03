package com.meokq.api.user.repository.queryDSL

import com.meokq.api.core.repository.Querydsl4RepositorySupport
import com.meokq.api.title.model.QTitle.title
import com.meokq.api.user.model.Customer
import com.meokq.api.user.model.QCustomer.customer
import com.meokq.api.title.model.QTitleHistory.titleHistory
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.response.CustomerXpLankResp
import com.meokq.api.user.response.XpRankCustomerResp
import com.meokq.api.xp.model.QXp.xp
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import org.springframework.aot.hint.TypeReference.listOf
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class CustomerQueryDSLRepository : Querydsl4RepositorySupport(Customer::class.java) {

    fun getXpRanking(con: RankSearchCondition): List<XpRankCustomerResp> {
        return queryFactory.select(
            Projections.constructor(
                XpRankCustomerResp::class.java, customer, xp.xpType, xp.xpPoint.max(), title
            )  // MAX로 xpPoint 처리
        )
            .from(customer)
            .leftJoin(customer.xp, xp)
            .leftJoin(titleHistory).on(customer.titleHistoryId.eq(titleHistory.id))
            .leftJoin(title).on(title.id.eq(titleHistory.titleId))
            .where(
                xp.xpType.eq(con.xpType)
            )
            .groupBy(xp.xpType, customer)
            .orderBy(xp.xpPoint.max().desc())  // MAX로 집계된 xpPoint의 내림차순 정렬
            .limit(con.size.toLong())
            .fetch()
    }

    fun getTopUsersByXp(limit: Long): List<CustomerXpLankResp> {
        val result = queryFactory
            .select(
                Projections.constructor(
                    CustomerXpLankResp::class.java,
                    customer.customerId,
                    customer.nickname,
                    xp.xpPoint.sum(),
                    customer.profileImageId,
                    Expressions.constant(0),
                    title,// 초기 lank 값을 0으로 설정
                )
            )
            .from(customer)
            .leftJoin(customer.xp, xp)
            .leftJoin(titleHistory).on(customer.titleHistoryId.eq(titleHistory.id))
            .leftJoin(title).on(title.id.eq(titleHistory.titleId))
            .groupBy(customer.customerId)
            .orderBy(xp.xpPoint.sum().desc())
            .limit(limit)
            .fetch()

        // 순위 매기기
        return result.withIndex().map { (index, resp) ->
            CustomerXpLankResp(
                customerId = resp.customerId,
                nickname = resp.nickname,
                xpSum = resp.xpSum,
                profileImage = resp.profileImage,
                lank = index + 1, // 순위는 1부터 시작
                title = resp.title
            )
        }
    }


}
