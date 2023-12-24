package com.meokq.api.market.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.market.model.Market

object MarketSpecifications : BaseSpecificationV2<Market> {
    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("marketId"),
            SpecificationDto("presidentId"),
            SpecificationDto("district"),
            SpecificationDto("status")
        )
}