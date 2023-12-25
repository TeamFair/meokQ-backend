package com.meokq.api.ticket

class TicketSearchDto(
    var recordId : String? = null,
    var marketId : String? = null,
    var pmtResult : PmtResult? = null,
    var traceType: TraceType? = null,
)