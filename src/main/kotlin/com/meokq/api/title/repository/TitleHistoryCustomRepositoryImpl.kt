package com.meokq.api.title.repository

import com.meokq.api.title.model.QTitle.title
import com.meokq.api.title.model.QTitleHistory
import com.meokq.api.title.model.QTitleHistory.titleHistory
import com.meokq.api.title.response.TitleHistoryRankResp
import com.meokq.api.title.response.TitleHistoryResp
import com.meokq.api.user.model.QCustomer.customer
import com.meokq.api.xp.model.QXp.xp
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory

class TitleHistoryCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : TitleHistoryCustomRepository {

    override fun findAllForUnRead(userId: String): List<TitleHistoryResp> {
        return queryFactory
            .select(
                Projections.constructor(
                    TitleHistoryResp::class.java,
                    titleHistory,
                    title,
                )
            )
            .from(titleHistory)
            .innerJoin(title).on(titleHistory.titleId.eq(title.id))
            .where(titleHistory.readYn.eq(false).and(titleHistory.customer.customerId.eq(userId)))
            .fetch()
    }

    override fun findAllByCustomerId(userId: String): List<TitleHistoryResp> {
        return queryFactory
            .select(
                Projections.constructor(
                    TitleHistoryResp::class.java,
                    titleHistory,
                    title,
                )
            )
            .from(title)
            .leftJoin(titleHistory).on(title.id.eq(titleHistory.titleId))
            .where(title.useYn.eq(true))
            .orderBy(title.createDate.asc())
            .fetch()
    }

    override fun findAllByRank(titleId: String): List<TitleHistoryRankResp> {
        var customerTitleHistory = QTitleHistory("customerTitleHistory")
        return queryFactory
            .select(
                Projections.constructor(
                    TitleHistoryRankResp::class.java,
                    customer,
                    titleHistory,
                    title,
                    JPAExpressions
                        .select(xp.xpPoint.sum())
                        .from(xp)
                        .where(xp.customer.eq(customer))
                )
            )
            .from(titleHistory)
            .innerJoin(titleHistory.customer, customer)
            .innerJoin(customerTitleHistory).on(customer.titleHistoryId.eq(customerTitleHistory.id))
            .innerJoin(title).on(customerTitleHistory.titleId.eq(title.id))
            .where(titleHistory.titleId.eq(titleId))
            .orderBy(titleHistory.createDate.asc())
            .fetch()
    }

}
