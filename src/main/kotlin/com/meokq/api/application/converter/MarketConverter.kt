package com.meokq.api.application.converter

import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketReq
import com.meokq.api.application.response.MarketDetailResp
import com.meokq.api.application.response.MarketResp
import org.springframework.stereotype.Component

@Component
class MarketConverter : BaseConverter<MarketReq, MarketResp, Market> {
    override fun modelToResponse(model: Market): MarketResp {
        return MarketResp(
            name = model.name,
            address = model.address,
            phone = model.phone,
            district = model.district,
            logoImage = model.logoImage,
            marketId = model.marketId,
            status = model.status
        )
    }

    override fun requestToModel(request: MarketReq): Market {
        return Market(
            name = request.name,
            address = request.address,
            phone = request.phone,
            district = request.district,
            logoImage = request.logoImage
        )
    }

    fun modelToDetailResponse(model: Market) : MarketDetailResp{
        return MarketDetailResp(
            name = model.name,
            address = model.address,
            phone = model.phone,
            district = model.district,
            logoImage = model.logoImage,
            ticketCount = model.ticketCount,
            marketId = model.marketId
        )
    }
}