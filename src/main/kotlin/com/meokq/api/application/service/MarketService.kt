package com.meokq.api.application.service

import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.MarketConverter
import com.meokq.api.application.model.Market
import com.meokq.api.application.repository.MarketRepository
import com.meokq.api.application.request.MarketReq
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.ImageResp
import com.meokq.api.application.response.MarketResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MarketService(
    private final val repository: MarketRepository,
    private final val converter: MarketConverter,
    private final val marketTimeService: MarketTimeService,
    private final val imageService: ImageService,
) : BaseService<MarketReq, MarketResp, Market, String> {
    override var _converter: BaseConverter<MarketReq, MarketResp, Market> = converter
    override var _repository: JpaRepository<Market, String> = repository

    fun findAll(searchDto: MarketSearchDto, pageable: Pageable): Page<MarketResp> {
        val page = repository.findByDistrict(searchDto.district, pageable)
        val content = page.content.map {
            val resp = converter.modelToResponse(it)

            // find market logo-image
            resp.logoImage = it.logoImageId?.let { it1 -> imageService.findById(it1) }
            resp
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    override fun save(request: MarketReq) : MarketResp {
        checkNotNull(request.presidentId)

        // save market
        val model = converter.requestToModel(request)
        val savedModel = repository.save(model)

        // save market-time
        val marketTimeReq = request.marketTime
        marketTimeReq.forEach {it.marketId = savedModel.marketId}
        if (marketTimeReq.isNotEmpty()) marketTimeService.saveAll(marketTimeReq)

        return converter.modelToCreatedResponse(savedModel)
    }

    fun findById(marketId: String): MarketResp? {
        // find market-model
        val model = repository.findById(marketId)
            .orElseThrow { throw NotFoundException("No market-data matching the criteria was found") }

        // find market-time-model
        val marketTimeReqList = marketTimeService.findAllByMarketId(marketId)

        // find market logo-image
        val logoImage = model.logoImageId?.let {
            try {
                imageService.findById(it)
            } catch (e: NotFoundException){
                ImageResp(imageId = null)
                //throw e
            }
        }

        // model to response
        return converter.modelToDetailResponse(model).also {
                it.marketTime = marketTimeReqList
                it.logoImage = logoImage
            }
    }
}