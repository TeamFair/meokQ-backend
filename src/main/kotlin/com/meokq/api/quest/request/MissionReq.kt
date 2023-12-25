package com.meokq.api.quest.request

import com.meokq.api.quest.enums.MissionType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Mission-Request")
class MissionReq(
    @Schema(description = "미션 설명(자유형식)", example = "10회 이상 방문")
    val content: String?,

    @Schema(description = "미션 대상", example = "")
    val target: String?,

    @Schema(description = "미션 수량", example = "")
    val quantity: Int?,

    @Schema(description = "미션 종류", example = "FREE")
    val type: MissionType
)