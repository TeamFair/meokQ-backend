package com.meokq.api.logs.repository

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.logs.model.XpHistory

object XpHisSpecification: BaseSpecificationV2<XpHistory> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("title"),
            SpecificationDto("userId"),
        )
}