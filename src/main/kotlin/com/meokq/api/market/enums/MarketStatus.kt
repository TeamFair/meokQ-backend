package com.meokq.api.market.enums

enum class MarketStatus(
    val couldDelete : Boolean
) {
    REGISTERED(couldDelete = true),     // 등록됨
    APPROVED(couldDelete = true),       // 승인
    REJECTED(couldDelete = false),      // 반려
    UNDER_REVIEW(couldDelete = false),  // 검토중
}