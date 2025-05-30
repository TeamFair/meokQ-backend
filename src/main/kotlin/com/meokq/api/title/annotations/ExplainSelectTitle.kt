package com.meokq.api.title.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ITI001) Title 전체 조회",
    description = """
        Title 상세정보를 조회합니다.
    """,
    parameters = [
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
  "data": [
    {
      "id": "TQ00001",
      "name": "2025년 일상의 개척자",
      "condition": "회원가입 시",
      "type": "STANDARD",
      "useYn": true
    }
  ],
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectTitle()
