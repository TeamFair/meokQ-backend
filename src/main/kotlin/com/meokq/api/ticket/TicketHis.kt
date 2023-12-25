package com.meokq.api.ticket

import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_ticket_pmt_history")
class TicketHis(
    @Id
    @UuidGenerator
    var recordId : String? = null,
    var marketId : String? = null,
    var amount : Long? = null,
    var questId : String? = null,               // (사용) 연관된 도전 내역
    var pmtPrice : Long? = null,                    // (구매) 결제금액
    var pmtResult : PmtResult = PmtResult.PENDING,  // (구매) 결제결과
    var traceType: TraceType? = null,

    ) : BaseModel() {
    constructor(req: TicketPmtReq) : this(
        marketId = req.marketId,
        amount = req.amount,
    )
}