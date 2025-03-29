package com.meokq.api.challenge.response

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.quest.model.Quest
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Challenge-Response")
open class ChallengeResp(
    @Schema(description = "Unique identifier for the challenge", example = "CH00001")
    val challengeId : String?,

    @Schema(description = "영수증 이미지의 주소")
    val receiptImage : String?,

    @Schema(description = "도전 내역 상태")
    val status : ChallengeStatus?,
){
    constructor(model: Challenge, quest: Quest?): this(
        challengeId = model.challengeId,
        receiptImage = model.receiptImageId,
        status = model.status,
    )
}
