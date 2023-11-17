package com.meokq.api.application.converter

import com.meokq.api.application.model.MarketTime
import com.meokq.api.application.request.MarketTimeReq
import com.meokq.api.application.response.MarketTimeResp
import org.springframework.stereotype.Component

@Component
class MarketTimeConverter : BaseConverter<MarketTimeReq, MarketTimeResp, MarketTime> {
    override fun modelToResponse(model: MarketTime): MarketTimeResp {
        return MarketTimeResp(
            weekDay = model.weekDay,
            openTime = model.openTime,
            closeTime = model.closeTime,
            holidayYn = model.holidayYn,
        )
    }

    override fun requestToModel(request: MarketTimeReq): MarketTime {
        return MarketTime(
            weekDay = request.weekDay,
            openTime = request.openTime,
            closeTime = request.closeTime,
            marketId = request.marketId,
            holidayYn = request.holidayYn,
        )
    }
}