package com.meokq.api.challenge.specification

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.market.request.ChallengeSearchDto
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object ChallengeSpecifications {
    fun bySearchDto(searchDto: ChallengeSearchDto): Specification<Challenge> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.marketId != null) {
                predicates.add(equal(
                    root.get("marketId"),
                    searchDto.marketId,
                    criteriaBuilder
                ))
            }

            if (searchDto.status != null) {
                predicates.add(
                    equal(
                        root.get("status"),
                        searchDto.status,
                        criteriaBuilder
                    )
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    fun <T> equal(expression: Expression<T>, value: T, criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder): Predicate {
        return criteriaBuilder.equal(expression, value)
    }

    fun like(expression: Expression<String>, pattern: String, criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder): Predicate {
        return criteriaBuilder.like(expression, pattern)
    }
}