package com.meokq.api.quest.request

import com.meokq.api.quest.enums.MissionType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Mission-Request")
class MissionReq(
    @Schema(description = "미션 설명(자유형식)")
    val content: String?,

    @Schema(description = "미션 대상")
    val target: String?,

    @Schema(description = "미션 수량")
    val quantity: Int?,

    @Schema(description = "미션 종류")
    val type: MissionType
)