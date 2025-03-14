package com.meokq.api.quiz.response;

import com.meokq.api.quest.response.AnswerResp
import com.meokq.api.quiz.model.QuizEntity
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Quiz-Response")
data class QuizResp(
    @Schema(description = "퀴즈 ID")
    val quizId: String? = null,
    @Schema(description = "질문")
    val question: String? = null,
    @Schema(description = "힌트")
    val hint: String? = null,
    @Schema(description = "정답")
    val answers: List<AnswerResp>? = null,
) {
    constructor(quiz : QuizEntity) : this(
        quizId = quiz.quizId,
        question = quiz.question,
        hint = quiz.hint,
        answers = quiz.answers.map { AnswerResp(it) },
    )
}
