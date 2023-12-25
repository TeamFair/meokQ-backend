package com.meokq.api.quest.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min

class QuestPublishReq(
    @Schema(example = "QS10000001")
    val questId : String,
    @Schema(example = "MK00000001")
    val marketId : String, //TODO : 추후 토큰에 포함하는 것을 고려
    @field:Min(1)
    @Schema(example = "2")
    val ticketCount : Long,
)