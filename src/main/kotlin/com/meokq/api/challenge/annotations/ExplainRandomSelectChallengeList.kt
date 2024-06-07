package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH004) 도전내역 랜덤 조회",
    description = "모든 도전내역을 랜덤 알고리즘을 통해 조회합니다.",
)

@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """

        """)])]
)
annotation class ExplainRandomSelectChallengeList
