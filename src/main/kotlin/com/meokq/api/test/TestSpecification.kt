package com.meokq.api.test

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto

object TestSpecification : BaseSpecificationV2<TestModel> {
    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("id"),
            SpecificationDto("param1"),
        )
}