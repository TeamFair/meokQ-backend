package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.reposone.MarketAuthResp
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.request.MarketAuthReviewReq
import com.meokq.api.market.service.MarketAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "MarketAuth", description = "마켓(점포) 인증")
@RestController
@RequestMapping("/api/markets/auth")
class MarketAuthController(
    private final val service : MarketAuthService,
) : BaseController<MarketAuthReq, MarketAuthResp, MarketAuth, String> {
    override val _service: BaseService<MarketAuthReq, MarketAuthResp, MarketAuth, String> = service

    @Operation(
        summary = "마켓인증내역 등록",
        description = "마켓 인증 내역을 등록합니다."
    )
    @PostMapping
    override fun save(request : MarketAuthReq) : ResponseEntity<BaseResp>{
        return super.save(request)
    }

    @Operation(
        summary = "마켓인증결과 등록",
        description = "마켓 인증결과를 등록합니다."
    )
    @PutMapping("/review")
    fun review(request : MarketAuthReviewReq) : ResponseEntity<BaseResp>{
        return ResponseEntity.ok(BaseResp(service.reviewMarketAuth(request)))
    }
}