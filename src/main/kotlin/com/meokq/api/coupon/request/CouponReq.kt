package com.meokq.api.coupon.request

data class CouponReq(
    val userId : String,
    val marketId : String,
    var challengeId : String,
)