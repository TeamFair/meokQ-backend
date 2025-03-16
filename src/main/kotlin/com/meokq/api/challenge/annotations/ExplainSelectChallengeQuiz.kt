package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH010) 퀴즈 챌린지 조회",
    description = "사용자앱에서 퀴즈 챌린지를 조회할 때 사용합니다.",
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
                {
                  "data": [
                    {
                      "challengeId": "CH00000100",
                      "status": "UNDER_REVIEW",
                      "questId": "1",
                      "customerId": "user1",
                      "answers": []
                    }
                  ],
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainSelectChallengeQuiz