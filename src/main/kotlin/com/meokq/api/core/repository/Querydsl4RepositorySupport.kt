package com.meokq.api.core.repository

import com.meokq.api.quest.model.QQuest
import com.meokq.api.quest.response.QuestListResp
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.querydsl.SimpleEntityPathResolver
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
abstract class Querydsl4RepositorySupport(
    private val domainClass: Class<*>,
) {
    @Autowired
    private lateinit var entityManager: EntityManager
    private lateinit var querydsl: Querydsl
    lateinit var queryFactory: JPAQueryFactory

    init {
        requireNotNull(domainClass) { "Domain class must not be null!" }
    }

    @PostConstruct
    fun init() {
        val entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager)
        val resolver = SimpleEntityPathResolver.INSTANCE
        val path = resolver.createPath(entityInformation.javaType)
        this.querydsl = Querydsl(entityManager, PathBuilder(path.type, path.metadata))
        this.queryFactory = JPAQueryFactory(entityManager)
    }

    protected fun <T> select(expr: Expression<T>): JPAQuery<T> {
        return queryFactory.select(expr)
    }

    protected fun <T> selectFrom(from: EntityPath<T>): JPAQuery<T> {
        return queryFactory.selectFrom(from)
    }

    protected fun <T> applyPagination(
        pageable: Pageable,
        contentQuery: (JPAQueryFactory) -> JPAQuery<T>,
        countQuery: (JPAQueryFactory) -> JPAQuery<Long>
    ): Page<T> {
        val jpaContentQuery = contentQuery.invoke(queryFactory)
        val content = querydsl.applyPagination(pageable, jpaContentQuery).fetch()
        val countResult = countQuery.invoke(queryFactory).fetchOne() ?: 0L
        return PageableExecutionUtils.getPage(content, pageable, { countResult} )
    }
}