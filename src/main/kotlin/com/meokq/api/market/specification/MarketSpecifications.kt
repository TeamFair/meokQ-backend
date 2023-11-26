package com.meokq.api.market.specification

import com.meokq.api.market.model.Market
import com.meokq.api.market.request.MarketSearchDto
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object MarketSpecifications {

    fun bySearchDto(searchDto: MarketSearchDto): Specification<Market> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.district != null) {
                predicates.add(equal(root.get("district"), searchDto.district, criteriaBuilder))
            }

            if (searchDto.presidentId != null) {
                predicates.add(equal(root.get("presidentId"), searchDto.presidentId, criteriaBuilder))
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