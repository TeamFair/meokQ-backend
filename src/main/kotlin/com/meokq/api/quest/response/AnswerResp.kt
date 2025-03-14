package com.meokq.api.quest.response

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.ValidationException

@Schema(name = "Answer-Response")
data class AnswerResp(
    @Schema(description = "정답", example = "10회 이상 방문")
    val content: String,
) {
    constructor(model: AnswerEntity) : this(
        content = model.content,
    )
}
