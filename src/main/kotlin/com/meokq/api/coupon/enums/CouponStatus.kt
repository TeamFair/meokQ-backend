package com.meokq.api.coupon.enums

enum class CouponStatus(val value : String) {
    ISSUED("발행"),
    USED("사용"),
    EXPIRED("만료")
}