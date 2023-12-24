package com.meokq.api.market.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.market.model.Market
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.request.MarketReq
import org.springframework.stereotype.Component

@Component
class MarketConverter : BaseConverter<MarketReq, MarketResp, Market> {
    override fun modelToResponse(model: Market): MarketResp {
        return MarketResp()
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
}