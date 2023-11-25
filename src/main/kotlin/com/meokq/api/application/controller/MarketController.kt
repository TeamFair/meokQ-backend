package com.meokq.api.application.controller

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketReq
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.MarketResp
import com.meokq.api.application.service.MarketService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Market", description = "마켓(점포)")
@RestController
@RequestMapping("/api/markets")
class MarketController(
    final val service : MarketService
) : BaseController<MarketReq, MarketResp, Market, String> {
    override val _service: BaseService<MarketReq, MarketResp, Market, String> = service

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
    ) : ResponseEntity<BaseListResp<MarketResp>> {

        val result = service.findAll(
            searchDto = MarketSearchDto(
                district = district
            ),
            pageable = PageRequest.of(page, size)
        )

        return ResponseEntity.ok(
            BaseListResp(
            content = result.content,
            totalElements = result.totalElements,
            size = result.size,
            number = result.number)
        )
    }

    @Operation(
        summary = "마켓정보 세부정보 조회",
        description = "마켓 세부정보를 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "마켓 아이디", required = true),
        ]
    )
    @GetMapping("/{marketId}")
    fun findByMarketId(@PathVariable marketId: String) : ResponseEntity<BaseResp>{
        val result = service.findById(marketId)
        return ResponseEntity.ok(BaseResp(result, ErrorStatus.OK))
    }

    @Operation(
        summary = "마켓정보 등록",
        description = "마켓 정보를 등록합니다.",
    )
    @PostMapping
    override fun save(
        @Valid @RequestBody request : MarketReq,
    ) : ResponseEntity<BaseResp> {
        return super.save(request)
    }
}