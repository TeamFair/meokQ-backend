package com.meokq.api.quest.request

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quiz.model.QuizEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.validation.ValidationException

@Schema(name = "Answer-Request")
data class AnswerReq(
    @Schema(description = "정답", example = "10회 이상 방문")
    val content: String,
) {
    fun toEntity(quiz: QuizEntity): AnswerEntity {
        return AnswerEntity(
            content = content,
            quiz = quiz,
        )
    }
}
