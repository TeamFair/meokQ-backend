package com.meokq.api.core.config

import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.support.Querydsl

@Configuration
class QueryDSLConfig{

    @Bean
    fun jpaQueryFactory(entityManager: EntityManager): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }

    @Bean
    fun querydsl(entityManager: EntityManager): Querydsl {
        return Querydsl(entityManager, PathBuilder<Any>(Any::class.java, "alias"))
    }
}