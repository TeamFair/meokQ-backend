package com.meokq.api.coupon.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.service.BaseService
import com.meokq.api.coupon.annotations.ExplainSelectCouponList
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.request.CouponReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.service.CouponService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "Coupon", description = "쿠폰")
class CouponController(
    private val service : CouponService
) : BaseController<CouponReq, CouponResp, Coupon, String>{
    override val _service: BaseService<CouponReq, CouponResp, Coupon, String> = service

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

        return getListRespEntityV2(result)
    }
}