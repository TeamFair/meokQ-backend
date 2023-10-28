package com.meokq.api.application.enums

enum class CouponStatus(val value : String) {
    ISSUED("발행"),
    USED("사용"),
    EXPIRED("만료")
}