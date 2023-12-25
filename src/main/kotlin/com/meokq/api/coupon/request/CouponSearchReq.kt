package com.meokq.api.coupon.request

import com.meokq.api.coupon.enums.CouponStatus
import io.swagger.v3.oas.annotations.media.Schema

data class CouponSearchReq(
    var status: CouponStatus? = null,
    @Schema(example = "CS10000003")
    var userId: String? = null,
    @Schema(example = "MK00000001")
    var marketId : String? = null,
    var userDataOnly : Boolean? = false,
)