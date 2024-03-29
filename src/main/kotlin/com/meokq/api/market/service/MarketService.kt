package com.meokq.api.market.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.file.service.ImageService
import com.meokq.api.market.converter.MarketConverter
import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.enums.WeekDay
import com.meokq.api.market.model.Market
import com.meokq.api.market.model.identifier.MarketTimeId
import com.meokq.api.market.repository.MarketRepository
import com.meokq.api.market.reposone.MarketCreateResp
import com.meokq.api.market.reposone.MarketDetailResp
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketSearchDto
import com.meokq.api.market.request.MarketUpdReq
import com.meokq.api.market.specification.MarketSpecifications
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.service.QuestService
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
    private final val questService: QuestService,
) : BaseService<MarketReq, MarketResp, Market, String> {
    override var _converter: BaseConverter<MarketReq, MarketResp, Market> = converter
    override var _repository: JpaRepository<Market, String> = repository

    fun findAll(
        searchDto: MarketSearchDto,
        pageable: Pageable = Pageable.unpaged(),
        authReq: AuthReq
    ): Page<MarketResp> {
        if (searchDto.ownMarketOnly == true)
            searchDto.presidentId = authReq.userId

        // TODO : 사용자 타입에 따른 마켓조회

        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val specification = MarketSpecifications.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = page.content.map {
            // market-time
            val marketTime = try {
                marketTimeService.findById(
                    MarketTimeId(weekDay = WeekDay.getToday(), marketId = it.marketId)
                )
            }catch (e : Exception){
                null
            }

            // quest-count
            val questCount = questService.count(
                QuestSearchDto(marketId = it.marketId, status = QuestStatus.PUBLISHED)
            )

            MarketResp(
                model = it,
                marketTime = marketTime,
                questCount = questCount
            )
        }

        val count = repository.count(specification)
        return PageImpl(content, pageable, count)
    }

    fun findDetailById(marketId: String, authReq: AuthReq): MarketDetailResp {
        // find market-model
        val model = findModelById(marketId)

        // find market-time-model
        val marketTimes = marketTimeService.findAllByMarketId(marketId)

        // model to response
        return MarketDetailResp(
            model = model,
            marketTimes = marketTimes,
        )
    }

    fun saveMarket(
        request: MarketReq,
        authReq: AuthReq,
    ) : MarketCreateResp {
        checkNotNullData(authReq.userId, "관리자 정보가 없습니다.")

        // TODO : 권한 체크

        // save market
        val model = Market(request, bossId = authReq.userId!!)
        val savedModel = repository.save(model)

        // save market-time
        val marketTimeReq = request.marketTime
        marketTimeReq.forEach {it.marketId = savedModel.marketId}
        if (marketTimeReq.isNotEmpty()) marketTimeService.saveAll(marketTimeReq)

        return MarketCreateResp(model.marketId)
    }

    fun updateMarket(
        marketId: String,
        request: MarketUpdReq,
        authReq: AuthReq,
    ) {
        checkNotNullData(authReq.userId, "관리자 정보가 없습니다.")

        // TODO : 권한 체크
        val market = findModelById(marketId)
        if (!request.address.isNullOrBlank()) market.address = request.address
        if (!request.logoImageId.isNullOrBlank()) market.logoImageId = request.logoImageId
        if (!request.phone.isNullOrBlank()) market.phone = request.phone
        repository.save(market)

        if (request.marketTime.isNotEmpty()) {
            marketTimeService.deleteByMarketId(marketId)
            request.marketTime.forEach { it.marketId = marketId }
            marketTimeService.saveAll(request.marketTime)
        }
    }

    fun updateMarketStatus(marketId: String, newStatus: MarketStatus) {
        val market = repository.findById(marketId)
            .orElseThrow { NotFoundException("Market is not found with id: $marketId") }

        market.status = newStatus
        repository.save(market)
    }
}