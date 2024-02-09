package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.annotations.*
import com.meokq.api.market.model.Market
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketSearchDto
import com.meokq.api.market.request.MarketUpdReq
import com.meokq.api.market.service.MarketService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Tag(name = "Market", description = "마켓(점포)")
@RestController
@RequestMapping(value = ["/api"])
class MarketController(
    private val service : MarketService,
) : BaseController<MarketReq, MarketResp, Market, String> {
    override val _service: BaseService<MarketReq, MarketResp, Market, String> = service

    @ExplainSelectMarketList
    @GetMapping(value = ["/boss/market", "/customer/market", "/admin/market", "/open/market"])
    fun findAll(
        searchDto: MarketSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq(),
        )

        return getListRespEntityV2(result)
    }

    @ExplainSelectMarket
    @GetMapping(value = ["/boss/market/{marketId}", "/customer/market/{marketId}", "/admin/market/{marketId}", "/open/market/{marketId}"])
    override fun findById(@PathVariable marketId: String, ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.findDetailById(marketId, getAuthReq()))
    }

    @ExplainSaveMarket
    @PostMapping(value = ["/boss/market", ])
    @Transactional(rollbackFor = [Exception::class])
    override fun save(
        @Valid @RequestBody request : MarketReq,
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.saveMarket(request, getAuthReq()))
    }

    @ExplainUpdateMarket
    @PutMapping(value = ["/boss/market/{marketId}"])
    @Transactional(rollbackFor = [Exception::class])
    fun update(
        @PathVariable marketId: String,
        @Valid @RequestBody request: MarketUpdReq
    ): ResponseEntity<BaseResp> {
        return getRespEntity(service.updateMarket(marketId, request, getAuthReq()))
    }

    @ExplainRequestReviewMarket
    @PutMapping(value = ["/boss/market/{marketId}/request-review",])
    @Transactional(rollbackFor = [Exception::class])
    fun requestReview(@PathVariable marketId: String, ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.requestReview(marketId))
    }
}