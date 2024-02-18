package com.meokq.api.market.controller

import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.market.annotations.ExplainRequestReviewMarket
import com.meokq.api.market.annotations.ExplainReviewMarketAuth
import com.meokq.api.market.annotations.ExplainSaveMarketAuth
import com.meokq.api.market.annotations.ExplainSelectMarketAuthList
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.request.MarketAuthReviewReq
import com.meokq.api.market.request.MarketAuthSearchDto
import com.meokq.api.market.service.MarketAuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "MarketAuth", description = "마켓(점포) 인증")
class MarketAuthController(
    private val service : MarketAuthService,
) : ResponseEntityCreation {

    /**
     * curd endpoint
     */
    @ExplainSaveMarketAuth
    @PostMapping("/boss/market-auth")
    fun save(
        @RequestBody @Valid request : MarketAuthReq
    ) : ResponseEntity<BaseResp>{
        return getRespEntity(service.save(request), ErrorStatus.OK)
    }

    @ExplainSelectMarketAuthList
    @GetMapping(value = ["/admin/market-auth", "/boss/market-auth"])
    fun findAll(
        searchDto: MarketAuthSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size)
        )
        return getListRespEntity(result)
    }

    /**
     * review marketAuth
     */
    @ExplainRequestReviewMarket
    @PutMapping(value = ["/boss/market-auth/{marketId}/request-review",])
    @Transactional(rollbackFor = [Exception::class])
    fun requestReview(@PathVariable marketId: String, ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.requestReview(marketId))
    }

    @ExplainReviewMarketAuth
    @PutMapping("/admin/market-auth/review")
    fun submitReview(
        @RequestBody @Valid request : MarketAuthReviewReq
    ) : ResponseEntity<BaseResp>{
        return getRespEntity(service.submitReview(request))
    }
}