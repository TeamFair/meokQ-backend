package com.meokq.api.application.service

import com.meokq.api.application.model.Market
import com.meokq.api.application.repository.MarketRepository
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.MarketDetailResponse
import com.meokq.api.application.response.MarketResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.MarketConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MarketService(
    final val repository: MarketRepository,
    final val converter: MarketConverter,
    private final val marketTimeService: MarketTimeService,
    private final val bossService: BossService
) : BaseService<MarketRequest, MarketResponse, Market, String> {
    override var _converter: BaseConverter<MarketRequest, MarketResponse, Market> = converter
    override var _repository: JpaRepository<Market, String> = repository

    fun findByDistinct(searchDto: MarketSearchDto): Page<MarketResponse> {
        val pageable = searchDto.pageable
        val page = repository.findByDistrict(searchDto.district, pageable)
        val content = page.content.map {
            converter.modelToResponse(it)
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    override fun save(request: MarketRequest) : MarketResponse {
        // save boss
        val boss = bossService.save(request.president)

        // save market
        val model = converter.requestToModel(request)
        model.presidentId = boss.bossId
        val savedModel = repository.save(model)
        request.marketTime.let {
            marketTimeService.saveAll(it, savedModel.marketId)
        }

        return converter.modelToResponse(savedModel)
    }

    fun findById(marketId: String): MarketDetailResponse? {
        val model = repository.findById(marketId)
        if (model.isEmpty) throw Exception("No data matching the criteria was found")
        val response = converter.modelToDetailResponse(model.get())
        response.marketTime = marketTimeService.findAllByMarketId(marketId)
        return response
    }
}