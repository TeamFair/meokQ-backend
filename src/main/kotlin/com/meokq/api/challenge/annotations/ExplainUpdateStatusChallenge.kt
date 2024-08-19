package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH008) 도전 상태 변경",
    description = "도전 내역의 상태를 변경 합니다",
    parameters = [
        Parameter(name = "challengeId", description = "도전내역 아이디", required = true, example = "CH10000004"),
        Parameter(name = "status", description = "상태 명", required = true, example = "APPROVED,REPORTED,REJECTED")
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
                {
                  "data": {},
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainUpdateStatusChallenge

