package com.meokq.api.agreement.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.agreement.model.Agreement

object AgreementSpecification : BaseSpecificationV2<Agreement> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("agreementType"),
            SpecificationDto("userId"),
        )
}