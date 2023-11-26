package com.meokq.api.market.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.market.converter.MarketConverter
import com.meokq.api.market.model.Market
import com.meokq.api.market.repository.MarketRepository
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketSearchDto
import com.meokq.api.image.response.ImageResp
import com.meokq.api.image.service.ImageService
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.specification.MarketSpecifications
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import org.springframework.data.domain.*
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
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        val specification = MarketSpecifications.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = page.content.map {
            val resp = converter.modelToResponse(it)

            // find market logo-image
            /*resp.logoImage = it.logoImageId?.let { imgId ->
                try {
                    imageService.findById(imgId)
                } catch (e: NotFoundException){
                    ImageResp(imageId = null)
                    //throw e
                }
            }*/
            resp
        }
        return PageImpl(content, pageable, page.numberOfElements.toLong())
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
}