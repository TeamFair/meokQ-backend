package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU013) 퀘스트 미션의 랜덤 퀴즈 추출",
    description = """
        Quest Mission의 퀴즈 중 하나를 랜덤 조회합니다.
    """,
    parameters = [
        Parameter(name = "missionId", description = "missionId", required = true, example = "ecbfa865-2da4-460c-8fd3-e49ad42534aa"),
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
	
Response body
Download
{
  "data": {
    "quizId": "b47f356f-554b-4df6-9e61-85e8a88c9d21",
    "question": "string1",
    "hint": "string1",
    "answers": [
      {
        "content": "X"
      },
      {
        "content": "O"
      }
    ]
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainRandomQuiz()
