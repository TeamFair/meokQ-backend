package com.meokq.api.coupon.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.coupon.annotations.ExplainSelectCouponList
import com.meokq.api.coupon.annotations.ExplainUseCoupon
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.service.CouponService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@Tag(name = "Coupon", description = "쿠폰")
class CouponController(
    private val service : CouponService
) : ResponseEntityCreation, AuthDataProvider{

    @ExplainSelectCouponList
    @GetMapping(value = ["/customer/coupon", "/boss/coupon", ])
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

        return getListRespEntity(result)
    }

    @ExplainUseCoupon
    @PutMapping("/customer/coupon/{couponId}/use")
    fun useCoupon(
        @PathVariable couponId: String
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.useCoupon(couponId, getAuthReq()))
    }
}