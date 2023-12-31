package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH001) 도전 내역 등록",
    description = "사용자앱에서 제출하기 버튼을 눌렀을 때, 정보를 바디에 담아서 호출해주세요.",
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
                {
                  "data": {
                    "challengeId": "CH00000100"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainSaveChallenge
