package com.meokq.api.core.converter

import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.response.MarketDetailResponse
import com.meokq.api.application.response.MarketResponse
import org.springframework.stereotype.Component

@Component
class MarketConverter : BaseConverter<MarketRequest, MarketResponse, Market> {
    override fun modelToResponse(model: Market): MarketResponse {
        return MarketResponse(
            name = model.name,
            address = model.address,
            phone = model.phone,
            district = model.district,
            logoImage = model.logoImage,
            marketId = model.marketId,
            status = model.status
        )
    }

    override fun requestToModel(request: MarketRequest): Market {
        return Market(
            name = request.name,
            address = request.address,
            phone = request.phone,
            district = request.district,
            logoImage = request.logoImage
        )
    }

    fun modelToDetailResponse(model: Market) : MarketDetailResponse{
        return MarketDetailResponse(
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