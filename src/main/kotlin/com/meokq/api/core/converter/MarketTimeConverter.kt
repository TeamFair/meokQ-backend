package com.meokq.api.core.converter

import com.meokq.api.application.model.MarketTime
import com.meokq.api.application.model.MarketTimeId
import com.meokq.api.application.request.MarketTimeRequest
import com.meokq.api.application.response.MarketTimeResponse
import org.springframework.stereotype.Component

@Component
class MarketTimeConverter : BaseConverter<MarketTimeRequest, MarketTimeResponse, MarketTime> {
    override fun modelToResponse(model: MarketTime): MarketTimeResponse {
        return MarketTimeResponse(
            weekDay = model.weekDay,
            openTime = model.openTime,
            closeTime = model.closeTime
        )
    }

    override fun requestToModel(request: MarketTimeRequest): MarketTime {
        return MarketTime(
            weekDay = request.weekDay,
            openTime = request.openTime,
            closeTime = request.closeTime
        )
    }
}