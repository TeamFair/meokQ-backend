package com.meokq.api.challenge.request

import io.swagger.v3.oas.annotations.media.Schema

data class ChallengeSaveReq(
    @Schema(description = "quest ID", example = "QS10000001")
    val questId : String,

    @Schema(description = "영수증 이미지 아이디", example = "IM10000001")
    val receiptImageId : String,
)