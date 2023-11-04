package com.meokq.api.application.service

import com.meokq.api.application.repository.MarketRepository
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.MarketDetailResponse
import com.meokq.api.application.response.MarketResponse
import com.meokq.api.core.converter.MarketConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service

@Service
class MarketService {

    @Autowired
    lateinit var repository : MarketRepository

    @Autowired
    lateinit var converter : MarketConverter

    @Autowired
    lateinit var marketTimeService: MarketTimeService

    fun findAll(searchDto: MarketSearchDto): Page<MarketResponse> {
        val pageable = searchDto.pageable
        val page = repository.findByDistrict(searchDto.district, pageable)
        val content = page.content.map {
            converter.modelToResponse(it)
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    fun save(request: MarketRequest) : MarketResponse {
        val model = converter.requestToModel(request)
        val savedModel = repository.save(model)
        request.marketTime?.let {
            marketTimeService.saveAll(it, savedModel.marketId)
        }

        return converter.modelToResponse(savedModel)
    }

    fun findByMarketId(marketId: String): MarketDetailResponse? {
        val model = repository.findById(marketId)
        if (model.isEmpty) throw Exception("No data matching the criteria was found")
        val response = converter.modelToDetailResponse(model.get())
        response.marketTime = marketTimeService.findAllByMarketId(marketId)
        return response
    }
}