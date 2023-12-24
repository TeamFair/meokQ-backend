package com.meokq.api.coupon.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.coupon.model.Coupon

object CouponSpec : BaseSpecificationV2<Coupon> {
    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("status"),
            SpecificationDto("userId"),
            SpecificationDto("marketId"),
        )
}