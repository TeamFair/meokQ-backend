package com.meokq.api.challenge.request

import com.meokq.api.challenge.enums.ChallengeStatus
import io.swagger.v3.oas.annotations.media.Schema

data class ChallengeSearchDto(
    @Schema(example = "MK00000001")
    val marketId : String? = null,
    val status : ChallengeStatus? = null,
    @Schema(example = "CS10000001")
    var userId : String? = null,
    @Schema(example = "true")
    val userDataOnly : Boolean = false,
    @Schema(example = "QS10000001")
    val questId : String? = null,
)
