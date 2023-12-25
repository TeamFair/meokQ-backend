package com.meokq.api.ticket

import jakarta.validation.constraints.Min

class TicketPmtReq(
    val marketId : String,
    @field:Min(1)
    val amount : Long,
)
