package com.meokq.api.application.service

import com.meokq.api.application.model.MarketTime
import com.meokq.api.application.repository.MarketTimeRepository
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.request.MarketTimeRequest
import com.meokq.api.application.response.MarketTimeResponse
import com.meokq.api.core.converter.MarketTimeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MarketTimeService {

    @Autowired
    lateinit var repository : MarketTimeRepository

    @Autowired
    lateinit var converter : MarketTimeConverter

    fun saveAll(dataList : List<MarketTimeRequest>, marketId : String?): MutableList<MarketTime> {
        val modelList = converter.requestToModel(dataList)
        modelList.forEach { it->it.marketId = marketId }
        return repository.saveAll(modelList)
    }

    fun findAllByMarketId(marketId: String?) : List<MarketTimeResponse> {
        if (marketId == null) throw Exception("marketId is null!")
        val model = repository.findAllByMarketId(marketId)
        return converter.modelToResponse(model)
    }
}