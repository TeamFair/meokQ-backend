package com.meokq.api.challenge.request

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import io.swagger.v3.oas.annotations.media.Schema

data class ChallengeSearchDto(
    var status : ChallengeStatus? = null,
    @Schema(example = "CS10000001")
    var userId : String? = null,
    @Schema(example = "true")
    var userDataOnly : Boolean = false,
    @Schema(example = "QS10000001")
    var questId : String? = null,
){
    constructor(challengeSearchDto: ChallengeSearchDto, authReq: AuthReq) : this() {
        this.status = challengeSearchDto.status
        this.userDataOnly = challengeSearchDto.userDataOnly
        this.questId = challengeSearchDto.questId
        this.userId = if(userDataOnly) challengeSearchDto.userId ?: authReq.userId else null
    }
}
