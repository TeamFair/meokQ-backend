package com.meokq.api.coupon.request

import com.meokq.api.coupon.enums.CouponStatus

data class CouponSearchReq(
    var couponStatus: CouponStatus? = null,
    var userId: String? = null,
    var marketId : String? = null,
)