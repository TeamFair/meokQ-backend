package com.meokq.api.xp

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto

object XpHisSpecification: BaseSpecificationV2<XpHistory> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("title"),
            SpecificationDto("userId"),
        )
}