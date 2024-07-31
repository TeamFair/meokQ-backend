package com.meokq.api.xp.repository

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.xp.model.XpHistory

object XpHisSpecification: BaseSpecificationV2<XpHistory> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("title"),
            SpecificationDto("userId"),
        )
}