package com.meokq.api.quest.request

import io.swagger.v3.oas.annotations.media.Schema

class QuestDeleteReq(
    @Schema(example = "QS10000001")
    val questId : String,
    @Schema(example = "MK00000001")
    val marketId : String, //TODO : 추후 토큰에 포함하는 것을 고려
)