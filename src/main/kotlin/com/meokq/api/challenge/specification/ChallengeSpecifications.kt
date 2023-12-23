package com.meokq.api.challenge.specification

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto

object ChallengeSpecifications : BaseSpecificationV2<Challenge> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("marketId"),
            SpecificationDto("status"),
            SpecificationDto("questId"),
            SpecificationDto(columnName = "customerId", paramName = "userId")
        )
}