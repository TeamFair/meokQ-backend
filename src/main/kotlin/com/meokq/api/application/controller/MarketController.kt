package com.meokq.api.application.controller

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketRequest
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.BaseResponse
import com.meokq.api.application.response.MarketResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.MarketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Market", description = "마켓(점포)")
@RestController
@RequestMapping("/api/markets")
class MarketController(
    val service : MarketService
) : BaseController<MarketRequest, MarketResponse, Market, String>{
    override val _service: BaseService<MarketRequest, MarketResponse, Market, String> = service

    @Operation(
        summary = "마켓정보 조회",
        description = "마켓정보를 조회합니다.",
        parameters = [
            Parameter(name = "district", description = "법정동 코드", required = true),
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping
    fun findByDistinct(
        @RequestParam(required = true) district : String,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResponse<MarketResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val searchDto = MarketSearchDto(district = district, pageable=pageable)
        val result = service.findByDistinct(searchDto)
        return ResponseEntity.ok(BaseListResponse(
            content = result.content,
            totalElements = result.totalElements,
            size = result.size,
            number = result.number
        ))
    }

    @Operation(
        summary = "마켓정보 세부정보 조회",
        description = "마켓 세부정보를 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "마켓 아이디", required = true),
        ]
    )
    @GetMapping("/{marketId}")
    fun findByMarketId(@PathVariable marketId: String) : ResponseEntity<BaseResponse>{
        val result = service.findById(marketId)
        return ResponseEntity.ok(BaseResponse(result, ErrorStatus.OK))
    }

    @Operation(
        summary = "마켓정보 등록",
        description = "마켓 정보를 등록합니다.",
    )
    @PostMapping
    override fun save(@Valid @RequestBody request : MarketRequest) : ResponseEntity<BaseResponse> {
        return super.save(request)
    }
}