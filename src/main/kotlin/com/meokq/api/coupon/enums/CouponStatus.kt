package com.meokq.api.coupon.enums

enum class CouponStatus(
    val value : String,
    val couldUse : Boolean,
) {
    ISSUED("발행", true),
    USED("사용", false),
    EXPIRED("만료", false)
}