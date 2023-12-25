package com.meokq.api.ticket

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto

object TicketSpecification : BaseSpecificationV2<TicketHis> {
    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("recordId"),
            SpecificationDto("marketId"),
            SpecificationDto("pmtResult"),
            SpecificationDto("traceType"),
        )
}