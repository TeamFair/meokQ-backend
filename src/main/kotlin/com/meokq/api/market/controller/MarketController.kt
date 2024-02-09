package com.meokq.api.market.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.market.annotations.ExplainSaveMarket
import com.meokq.api.market.annotations.ExplainSelectMarket
import com.meokq.api.market.annotations.ExplainSelectMarketList
import com.meokq.api.market.annotations.ExplainUpdateMarket
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

@RestController
@RequestMapping(value = ["/api"])
@Tag(name = "Market", description = "마켓(점포)")
class MarketController(
    private val service : MarketService,
) : ResponseEntityCreation, AuthDataProvider {

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

        return getListRespEntity(result)
    }

    @ExplainSelectMarket
    @GetMapping(value = ["/boss/market/{marketId}", "/customer/market/{marketId}", "/admin/market/{marketId}", "/open/market/{marketId}"])
    fun findById(@PathVariable marketId: String, ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.findDetailById(marketId, getAuthReq()))
    }

    @ExplainSaveMarket
    @PostMapping(value = ["/boss/market", ])
    @Transactional(rollbackFor = [Exception::class])
    fun save(
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
}