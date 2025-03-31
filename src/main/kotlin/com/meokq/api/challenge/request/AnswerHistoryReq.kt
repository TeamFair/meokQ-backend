package com.meokq.api.challenge.request

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter

@Getter
class AnswerHistoryReq (
    @Schema(description = "퀴즈 ID", example = "QZ10000001")
    val quizId : String,

    @Schema(description = "퀴즈 정답", example = "1")
    val answer : String
)