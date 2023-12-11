package com.meokq.api.market.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.converter.MarketTimeConverter
import com.meokq.api.market.model.MarketTime
import com.meokq.api.market.model.identifier.MarketTimeId
import com.meokq.api.market.repository.MarketTimeRepository
import com.meokq.api.market.reposone.MarketTimeResp
import com.meokq.api.market.request.MarketTimeReq
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

    fun deleteByMarketId(marketId: String) : Int {
        return repository.deleteAllByMarketId(marketId)
    }
}