package com.meokq.api.challenge.request

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter

@Getter
class ChallengeQuizReq(
    @Schema(description = "quest ID", example = "QS10000001")
    val questId : String,
    val answers : List<AnswerHistoryReq> = listOf()
)