package com.meokq.api.user.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.user.model.Agreement

object AgreementSpecification : BaseSpecificationV2<Agreement> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("agreementType"),
            SpecificationDto("userId"),
        )
}