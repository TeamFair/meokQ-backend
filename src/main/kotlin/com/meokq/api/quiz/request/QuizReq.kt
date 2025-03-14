package com.meokq.api.quiz.request

import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.request.AnswerReq
import com.meokq.api.quiz.model.QuizEntity
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Quiz-Request")
data class QuizReq(
    @Schema(description = "질문", example = "")
    val question: String,
    @Schema(description = "힌트", example = "")
    val hint: String,
    val answers: List<AnswerReq>,
) {
    fun toEntity(mission: Mission): QuizEntity {
        return QuizEntity(
            this,
            mission,
        )
    }
}
