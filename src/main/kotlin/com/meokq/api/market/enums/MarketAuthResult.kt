package com.meokq.api.market.enums

enum class MarketAuthResult(val status: MarketStatus) {
    APPROVED(MarketStatus.APPROVED), // 승인
    REJECTED(MarketStatus.REJECTED), // 반려
}