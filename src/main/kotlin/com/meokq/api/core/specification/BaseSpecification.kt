package com.meokq.api.core.specification

import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

abstract class BaseSpecification<T, M> {
    abstract fun bySearchDto(searchDto : T) : Specification<M>

    fun <T> equal(expression: Expression<T>, value: T, criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder): Predicate {
        return criteriaBuilder.equal(expression, value)
    }

    fun like(expression: Expression<String>, pattern: String, criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder): Predicate {
        return criteriaBuilder.like(expression, pattern)
    }

    fun between(
        expression: Expression<LocalDateTime>,
        start: LocalDateTime,
        end: LocalDateTime,
        criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(expression, start, end)
    }
}