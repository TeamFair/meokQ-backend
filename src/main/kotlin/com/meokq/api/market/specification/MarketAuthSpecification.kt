package com.meokq.api.market.specification

import com.meokq.api.core.specification.BaseSpecification
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.request.MarketAuthSearchDto
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object MarketAuthSpecification : BaseSpecification<MarketAuthSearchDto, MarketAuth>() {
    override fun bySearchDto(searchDto: MarketAuthSearchDto): Specification<MarketAuth> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.marketId != null) {
                predicates.add(MarketSpecifications.equal(root.get("marketId"), searchDto.marketId, criteriaBuilder))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}