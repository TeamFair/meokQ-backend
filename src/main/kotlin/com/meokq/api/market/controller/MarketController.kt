package com.meokq.api.market.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.model.Market
import com.meokq.api.market.reposone.MarketResp
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketSearchDto
import com.meokq.api.market.service.MarketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
        summary = "(IMK001) 마켓정보 조회",
        parameters = [
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
{
  "size": 10,
  "data": [
    {
      "marketId": "MK00000001",
      "logoImage": {
        "imageId": "IM10000003",
        "location": "/path/to/image3"
      },
      "name": "Market Name",
      "district": "1100000000",
      "address": "서울특별시 종로구",
      "status": "APPROVED",
      "questCount": 1,
      "marketTime": {
        "weekDay": "SUN",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "Y"
      }
    }
  ],
  "total": 1,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
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
        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq(),
        )

        return getListRespEntityV2(result)
    }

    @Operation(
        summary = "(IMK006) 마켓정보 세부정보 조회",
        parameters = [
            Parameter(name = "marketId", description = "마켓 아이디", required = true, example = "MK00000001"),
        ]
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
{
  "data": {
    "marketId": "MK00000001",
    "name": "Market Name",
    "phone": "0211111111",
    "district": "1100000000",
    "address": "서울특별시 종로구",
    "status": "APPROVED",
    "ticketCount": 10,
    "marketTimes": [
      {
        "weekDay": "FRI",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "MON",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "SAT",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "SUN",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "Y"
      },
      {
        "weekDay": "THU",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "TUE",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "WED",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      }
    ]
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
    )
    @GetMapping(value = [
        "/boss/market/{marketId}",
        "/customer/market/{marketId}",
        "/admin/market/{marketId}",
        "/open/market/{marketId}"
    ])
    override fun findById(
        @PathVariable marketId: String,
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.findDetailById(marketId, getAuthReq()))
    }

    @Operation(
        summary = "(IMK003) 마켓정보 등록",
        description = "마켓 정보를 등록합니다.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            examples = [ExampleObject(value = """
{
  "data": {
    "marketId": "MK00000100"
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
    )
    @PostMapping(value = [
        "/boss/market",
    ])
    override fun save(
        @Valid @RequestBody request : MarketReq,
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.saveMarket(request, getAuthReq()))
    }
}