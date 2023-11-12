package com.meokq.api.application.service

import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.MarketConverter
import com.meokq.api.application.enums.MarketStatus
import com.meokq.api.application.model.Market
import com.meokq.api.application.repository.MarketRepository
import com.meokq.api.application.request.MarketReq
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.MarketDetailResp
import com.meokq.api.application.response.MarketResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
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
) : BaseService<MarketReq, MarketResp, Market, String> {
    override var _converter: BaseConverter<MarketReq, MarketResp, Market> = converter
    override var _repository: JpaRepository<Market, String> = repository

    fun findByDistinct(searchDto: MarketSearchDto): Page<MarketResp> {
        val pageable = searchDto.pageable
        val page = repository.findByDistrict(searchDto.district, pageable)
        val content = page.content.map {
            converter.modelToResponse(it)
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    override fun save(request: MarketReq) : MarketResp {
        // save boss
        val boss = bossService.save(request.president)

        // save market
        val model = converter.requestToModel(request)
        model.presidentId = boss.bossId
        val savedModel = repository.save(model)

        // save market-time
        val marketTimeReq = request.marketTime
        marketTimeReq.forEach {it.marketId = savedModel.marketId}
        if (marketTimeReq.isNotEmpty()) marketTimeService.saveAll(marketTimeReq)

        return converter.modelToResponse(savedModel)
    }

    fun findById(marketId: String): MarketDetailResp? {
        // find market-model
        val model = repository.findById(marketId)
            .orElseThrow { throw Exception("No data matching the criteria was found") }

        // find market-time-model
        val marketTimeReqList = marketTimeService.findAllByMarketId(marketId)

        // model to response
        return converter.modelToDetailResponse(model)
            .also { it.marketTime = marketTimeReqList }
    }

    fun getMarketStatusByEmail(email: String): MarketStatus {
        // find boss by email
        val boss = bossService.findByEmail(email)
        checkNotNull(boss.bossId)

        // find market by boss-id
        val market = findByPresidentId(boss.bossId)
            .firstOrNull() ?: throw NotFoundException("market is not found!")

        return market.status
    }

    fun findByPresidentId(presidentId : String): List<MarketResp> {
        return converter.modelToResponse(repository.findAllByPresidentId(presidentId))
    }
}