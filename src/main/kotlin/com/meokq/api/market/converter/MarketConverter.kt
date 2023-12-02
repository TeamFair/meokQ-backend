package com.meokq.api.market.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.market.model.Market
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.reposone.MarketResp
import org.springframework.stereotype.Component

@Component
class MarketConverter : BaseConverter<MarketReq, MarketResp, Market> {
    override fun modelToResponse(model: Market): MarketResp {
        return MarketResp(
            marketId = model.marketId,
            logoImage = null,
            name = model.name,
            phone = model.phone,
            district = model.district,
            address = model.address,
            status = model.status,
            ticketCount = null,
            marketTime = null
        )
    }

    override fun requestToModel(request: MarketReq): Market {
        return Market(
            name = request.name,
            address = request.address,
            phone = request.phone,
            district = request.district,
            logoImageId = request.logoImageId
        )
    }

    fun modelToDetailResponse(model: Market) : MarketResp {
        return MarketResp(
            marketId = model.marketId,
            logoImage = null,
            name = model.name,
            phone = model.phone,
            district = model.district,
            address = model.address,
            status = model.status,
            ticketCount = model.ticketCount,
            marketTime = null
        )
    }

    fun modelToCreatedResponse(model: Market) : MarketResp {
        return MarketResp(
            marketId = model.marketId,
            logoImage = null,
            name = null,
            phone = null,
            district = null,
            address = null,
            status = null,
            ticketCount = null,
            marketTime = null
        )
    }
}