package com.meokq.api.coupon.specification

import com.meokq.api.core.specification.BaseSpecification
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.request.CouponSearchReq
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object CouponSpec : BaseSpecification<CouponSearchReq, Coupon>() {
    override fun bySearchDto(searchDto: CouponSearchReq): Specification<Coupon> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.marketId != null) {
                predicates.add(
                    equal(
                        root.get("targetMarketId"),
                        searchDto.marketId,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.userId != null) {
                predicates.add(
                    equal(
                        root.get("targetUserId"),
                        searchDto.userId,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.couponStatus != null) {
                predicates.add(
                    equal(
                        root.get("couponStatus"),
                        searchDto.couponStatus,
                        criteriaBuilder
                    )
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}