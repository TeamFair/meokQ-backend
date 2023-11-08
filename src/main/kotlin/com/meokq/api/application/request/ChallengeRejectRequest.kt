package com.meokq.api.application.request

import jakarta.validation.constraints.Size

data class ChallengeRejectRequest(
    val marketId : String,
    val challengeId : String,
    @field: Size(max = 100, min = 10, message = "Reject reason must be between 10 and 100 characters")
    var rejectReason : String,
)
