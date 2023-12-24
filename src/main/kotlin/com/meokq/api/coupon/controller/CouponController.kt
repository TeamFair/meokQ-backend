package com.meokq.api.coupon.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.request.CouponReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.service.CouponService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "Coupon", description = "쿠폰")
class CouponController(
    private val service : CouponService
) : BaseController<CouponReq, CouponResp, Coupon, String>{
    override val _service: BaseService<CouponReq, CouponResp, Coupon, String> = service

    @GetMapping(value = [
        "/customer/coupon",
        "/boss/coupon",
    ])
    @Operation(
        summary = "(ICP001) 쿠폰 목록 조회",
        description = """
            사용자앱에서 호출하는 경우, userDataOnly가 true여야 합니다.(소유한 쿠폰만 조회할 수 있습니다.)
            보스앱에서 호출하는 경우, marketId가 null이 유효한 값이어야 합니다.(해당 마켓에서 발급한 쿠폰만 조회할 수 있습니다.)
        """,
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
                      "useDate": null,
                      "status": "ISSUED",
                      "couponId": "CP10000003",
                      "expireDate": null,
                      "marketId": "MK00000001",
                      "userNickname": "nickname1",
                      "reward": {
                        "rewardId": "RW00000002",
                        "content": null,
                        "target": "DONUT",
                        "quantity": 1,
                        "discountRate": null,
                        "type": "GIFT"
                      },
                      "missions": [
                        {
                          "content": null,
                          "target": "COFFEE",
                          "quantity": 3,
                          "type": "NORMAL"
                        }
                      ]
                    }
                  ],
                  "total": 1,
                  "page": 0,
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    fun findAll(
        searchReq: CouponSearchReq,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            searchDto = searchReq,
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq()
        )

        return getListRespEntityV2(result)
    }

    @PutMapping(value = ["/customer/coupon/{couponId}/use",])
    @Operation(
        summary = "(ICP002) 쿠폰 사용",
        description = "쿠폰을 사용합니다.",
        parameters = [
            Parameter(name = "couponId", description = "쿠폰 아이디", required = true, example = "CP10000001"),
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
                  "data": {},
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    fun useCoupon(@PathVariable couponId : String): ResponseEntity<BaseResp> {
        return getRespEntity(service.useCoupon(couponId))
    }
}