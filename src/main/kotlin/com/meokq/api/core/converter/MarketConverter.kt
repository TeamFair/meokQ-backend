package com.meokq.api.core.converter

import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.response.MarketResponse
import org.springframework.stereotype.Component

@Component
class MarketConverter : BaseConverter<MarketRequest, MarketResponse, Market> {
    override fun modelToResponse(model: Market): MarketResponse {
        return MarketResponse(
            name = model.name,
            address = model.address,
            marketId = model.marketId,
            phone = model.phone,
            district = model.district,
            logoImage = model.logoImage
        )
    }

    override fun requestToModel(request: MarketRequest): Market {
        TODO("Not yet implemented")
    }
}