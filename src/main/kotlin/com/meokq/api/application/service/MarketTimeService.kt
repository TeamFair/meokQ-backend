package com.meokq.api.application.service

import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.MarketTimeConverter
import com.meokq.api.application.model.MarketTime
import com.meokq.api.application.model.identifier.MarketTimeId
import com.meokq.api.application.repository.MarketTimeRepository
import com.meokq.api.application.request.MarketTimeReq
import com.meokq.api.application.response.MarketTimeResp
import com.meokq.api.core.service.BaseService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MarketTimeService(
    val repository: MarketTimeRepository,
    val converter : MarketTimeConverter
) : BaseService<MarketTimeReq, MarketTimeResp, MarketTime, MarketTimeId> {
    override var _converter: BaseConverter<MarketTimeReq, MarketTimeResp, MarketTime> = converter
    override var _repository: JpaRepository<MarketTime, MarketTimeId> = repository

    fun findAllByMarketId(marketId: String?) : List<MarketTimeResp> {
        if (marketId == null) throw Exception("marketId is null!")
        val model = repository.findAllByMarketId(marketId)
        return converter.modelToResponse(model)
    }
}