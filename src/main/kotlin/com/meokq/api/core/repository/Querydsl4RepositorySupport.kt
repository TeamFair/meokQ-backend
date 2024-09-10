package com.meokq.api.core.repository

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
    private val domainClass: Class<*>
) {
    private lateinit var querydsl: Querydsl
    private lateinit var entityManager: EntityManager
    lateinit var queryFactory: JPAQueryFactory

    init {
        requireNotNull(domainClass) { "Domain class must not be null!" }
    }

    @Autowired
    fun setEntityManager(entityManager: EntityManager) {
        val entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager)
        val resolver = SimpleEntityPathResolver.INSTANCE
        val path = resolver.createPath(entityInformation.javaType)
        this.entityManager = entityManager
        this.querydsl = Querydsl(entityManager, PathBuilder(path.type, path.metadata))
        this.queryFactory = JPAQueryFactory(entityManager)
    }

    @PostConstruct
    fun validate() {
        requireNotNull(entityManager) { "EntityManager must not be null!" }
        requireNotNull(querydsl) { "Querydsl must not be null!" }
        requireNotNull(queryFactory) { "QueryFactory must not be null!" }
    }


    protected fun <T> select(expr: Expression<T>): JPAQuery<T> {
        return queryFactory.select(expr)
    }

    protected fun <T> selectFrom(from: EntityPath<T>): JPAQuery<T> {
        return queryFactory.selectFrom(from)
    }

    protected fun <T> applyPagination(pageable: Pageable, contentQuery: (JPAQueryFactory) -> JPAQuery<T>): Page<T> {
        val jpaQuery = contentQuery.invoke(queryFactory)
        val content = querydsl.applyPagination(pageable, jpaQuery).fetch()
        return PageableExecutionUtils.getPage(content, pageable, jpaQuery::fetchCount)
    }

    protected fun <T> applyPagination(
        pageable: Pageable,
        contentQuery: (JPAQueryFactory) -> JPAQuery<T>,
        countQuery: (JPAQueryFactory) -> JPAQuery<*>
    ): Page<T> {
        val jpaContentQuery = contentQuery.invoke(queryFactory)
        val content = querydsl.applyPagination(pageable, jpaContentQuery).fetch()
        val countResult = countQuery.invoke(queryFactory)
        return PageableExecutionUtils.getPage(content, pageable, countResult::fetchCount)
    }
}