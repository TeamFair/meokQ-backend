package com.meokq.api.coupon.controller

import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.service.CouponService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/coupons")
class CouponController(
    private val service : CouponService
) {

    @GetMapping
    fun findAll(
        @RequestParam(required = false) marketId : String?,
        @RequestParam(required = false) userId : String?,
        @RequestParam(required = false) status : CouponStatus?,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<CouponResp>> {
        val result = service.findAll(
            searchDto = CouponSearchReq(
                couponStatus = status,
                userId = userId,
                marketId = marketId,
            ),
            pageable = PageRequest.of(page, size)
        )

        return ResponseEntity.ok(
            BaseListResp(
                content = result.content,
                totalElements = result.totalElements,
                size = result.size,
                number = 0
            )
        )
    }
}