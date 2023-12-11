package com.meokq.api.challenge.enums

enum class ChallengeStatus(
    val value : String,
    val couldDelete : Boolean
) {
    APPROVED("승인", false),
    REJECTED("반려", false),
    UNDER_REVIEW("검토중", true)
}