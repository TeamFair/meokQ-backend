package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.model.Market
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketSearchDto
import com.meokq.api.market.service.MarketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Market", description = "마켓(점포)")
@RestController
@RequestMapping(value = ["/api"])
class MarketController(
    private val service : MarketService,
) : BaseController<MarketReq, MarketResp, Market, String> {
    override val _service: BaseService<MarketReq, MarketResp, Market, String> = service

    @Operation(
        summary = "마켓정보 조회",
        parameters = [
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping(value = [
        "/boss/market",
        "/customer/market",
        "/admin/market",
        "/open/market"
    ])
    fun findAll(
        searchDto: MarketSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
        httpRequest: HttpServletRequest,
    ) : ResponseEntity<BaseListRespV2> {
        val authReq = getAuthReq()
        // TODO : 사용자 권한에 따른 응답값처리

        val result = service.findAll(
            searchDto = searchDto.also {
                if (it.ownMarketOnly == true) it.presidentId = authReq.userId
            },
            pageable = PageRequest.of(page, size)
        )

        return getListRespEntityV2(result)
    }

    @Operation(
        summary = "마켓정보 세부정보 조회",
        parameters = [
            Parameter(name = "marketId", description = "마켓 아이디", required = true),
        ]
    )
    @GetMapping(value = [
        "/boss/market/{marketId}",
        "/customer/market/{marketId}",
        "/admin/market/{marketId}",
        "/open/market/{marketId}"
    ])
    fun findByMarketId(
        @PathVariable marketId: String,
        httpRequest: HttpServletRequest,
    ) : ResponseEntity<BaseResp> {

        // 토큰으로 인증된 사용자인 경우,
        val result = service.findById(marketId)
        return ResponseEntity.ok(BaseResp(result, ErrorStatus.OK))
    }

    @Operation(
        summary = "마켓정보 등록",
        description = "마켓 정보를 등록합니다.",
    )
    @PostMapping(value = [
        "/boss/market",
    ])
    fun save(
        @Valid @RequestBody request : MarketReq,
        httpRequest: HttpServletRequest,
    ) : ResponseEntity<BaseResp> {
        return super.save(request)
    }

    @Operation(
        summary = "market 정보 삭제",
        description = """
            마켓정보는 BOSS와 ADMIN만 삭제할수 있습니다.
            승인된 마켓은 삭제할 수 없습니다.
        """,
        parameters = [
            Parameter(name = "marketId", description = "market 아이디", required = true),
        ]
    )
    /*@DeleteMapping(value = [
        "/boss/market/{marketId}",
        "/admin/market/{marketId}",
    ])*/
    @Deprecated("추후 보완")
    override fun deleteById(@PathVariable marketId: String) : ResponseEntity<BaseResp> {
        return super.deleteByIdWithAuth(marketId)
    }
}