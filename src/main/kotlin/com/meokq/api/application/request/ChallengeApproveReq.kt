package com.meokq.api.application.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Challenge-Approve-Request")
data class ChallengeApproveReq (
    val challengeId : String,
    val marketId : String,
)