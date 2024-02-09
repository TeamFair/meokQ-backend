package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.annotations.ExplainReviewMarketAuth
import com.meokq.api.market.annotations.ExplainSaveMarketAuth
import com.meokq.api.market.annotations.ExplainSelectMarketAuthList
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.reposone.MarketAuthResp
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.request.MarketAuthReviewReq
import com.meokq.api.market.request.MarketAuthSearchDto
import com.meokq.api.market.service.MarketAuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "MarketAuth", description = "마켓(점포) 인증")
class MarketAuthController(
    private val service : MarketAuthService,
) : BaseController<MarketAuthReq, MarketAuthResp, MarketAuth, String> {
    override val _service: BaseService<MarketAuthReq, MarketAuthResp, MarketAuth, String> = service

    @ExplainSaveMarketAuth
    @PostMapping("/boss/market-auth")
    override fun save(
        @RequestBody @Valid request : MarketAuthReq
    ) : ResponseEntity<BaseResp>{
        return super.save(request)
    }


    @ExplainReviewMarketAuth
    @PutMapping("/admin/market-auth/review")
    fun review(
        @RequestBody @Valid request : MarketAuthReviewReq
    ) : ResponseEntity<BaseResp>{
        return ResponseEntity.ok(BaseResp(service.reviewMarketAuth(request)))
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
        return getListRespEntityV2(result)
    }
}