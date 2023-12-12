package com.meokq.api.coupon.request

data class CouponReq(
    val targetUserId : String,
    val targetMarketId : String,
    var challengeId : String,
)