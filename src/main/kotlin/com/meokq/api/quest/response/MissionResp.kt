package com.meokq.api.quest.response

import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
import io.swagger.v3.oas.annotations.media.Schema


/**
 * 따로 연결된 정보가 없어서, 용도별로 response 를 나누지는 않았음.
 */
@Schema(description = "Mission-Response")
class MissionResp(
    @Schema(description = "미션 설명(자유형식)")
    val content: String?,

    @Schema(description = "미션 대상")
    val target: String?,

    @Schema(description = "미션 수량")
    val quantity: Int?,

    @Schema(description = "미션 종류")
    val type: MissionType?,

    val title: String? = null,
) {
    constructor(model : Mission) : this(
        content = model.content,
        target = model.target,
        quantity = model.quantity,
        type = model.type,
        title = model.type?.getTitle(model)
    )
}
