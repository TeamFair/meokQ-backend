package com.meokq.api.market.request

import com.meokq.api.challenge.enums.ChallengeStatus

data class ChallengeSearchDto(
    val marketId : String? = null,
    val status : ChallengeStatus? = null,
)
