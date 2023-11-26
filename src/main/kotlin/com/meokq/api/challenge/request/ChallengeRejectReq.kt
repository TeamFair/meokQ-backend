package com.meokq.api.challenge.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(name = "Challenge-Reject-Request")
data class ChallengeRejectReq(
    val marketId : String,
    val challengeId : String,
    @field: Size(max = 100, min = 10, message = "Reject reason must be between 10 and 100 characters")
    var rejectReason : String,
)
