package com.meokq.api.application.enums

enum class ChallengeStatus(val value : String) {
    APPROVED("승인"),
    REJECTED("반려"),
    UNDER_REVIEW("검토중")
}