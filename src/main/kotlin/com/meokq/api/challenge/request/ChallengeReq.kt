package com.meokq.api.challenge.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Challenge-Request")
class ChallengeReq(
    @Schema(description = "quest ID", example = "QS00000001")
    val questId : String,

    @Schema(description = "영수증 이미지 아이디", example = "IM00000001")
    val receiptImageId : String
)