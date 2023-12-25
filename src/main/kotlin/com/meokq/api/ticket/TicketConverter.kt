package com.meokq.api.ticket

import com.meokq.api.core.converter.BaseConverter

object TicketConverter : BaseConverter<TicketHis, TicketHis, TicketHis> {
    override fun modelToResponse(model: TicketHis) = model
    override fun requestToModel(request: TicketHis) = request
}