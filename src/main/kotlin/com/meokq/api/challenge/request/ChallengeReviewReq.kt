package com.meokq.api.challenge.request

import com.meokq.api.challenge.enums.ChallengeReviewResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Challenge-Reject-Request")
data class ChallengeReviewReq(
    @Schema(description = "도전내역 구분자")
    val challengeId : String,

    @Schema(description = "검토결과")
    val result : ChallengeReviewResult,

    @Schema(description = "검토의견")
    val comment : String?,
)
