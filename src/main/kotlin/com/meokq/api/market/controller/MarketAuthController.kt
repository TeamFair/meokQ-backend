package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.reposone.MarketAuthResp
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.request.MarketAuthReviewReq
import com.meokq.api.market.request.MarketAuthSearchDto
import com.meokq.api.market.service.MarketAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//@Tag(name = "MarketAuth", description = "마켓(점포) 인증")
@RestController
@RequestMapping("/api")
class MarketAuthController(
    private val service : MarketAuthService,
) : BaseController<MarketAuthReq, MarketAuthResp, MarketAuth, String> {
    override val _service: BaseService<MarketAuthReq, MarketAuthResp, MarketAuth, String> = service

    @Operation(
        summary = "마켓인증내역 등록",
        description = "마켓 인증 내역(대표자 개인정보, 영업신고증 정보)을 등록합니다.",
        tags = ["Market"],
    )
    @PostMapping
    override fun save(request : MarketAuthReq) : ResponseEntity<BaseResp>{
        return super.save(request)
    }

    @Operation(
        summary = "마켓인증 검토결과 등록",
        description = "ADMIN 타입의 사용자가 마켓인증 검토결과를 등록합니다.",
        tags = ["Market"],
    )
    @PutMapping("/review")
    fun review(request : MarketAuthReviewReq) : ResponseEntity<BaseResp>{
        return ResponseEntity.ok(BaseResp(service.reviewMarketAuth(request)))
    }

    @Operation(
        summary = "마켓 인증정보 조회",
        description = "마켓 인증정보를 조회합니다.",
        tags = ["Market"],
        parameters = [
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
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