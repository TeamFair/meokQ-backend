package com.meokq.api.title.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ITH003) Title History 전체 조회 - 안읽은 칭호",
    description = """
        읽지 않은 칭호를 전체 조회합니다.
    """,
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
      "titleHistory": {
        "id": "9b7ea924-0bf1-4fea-b7bf-48ee6a65c0ac",
        "createdAt": "2025-05-03T23:14:24"
      },
      "title": {
        "id": "TQ00001"
        "name": "2025년 일상의 개척자",
        "type": "STANDARD",
        "createdAt": "2025-05-03T17:19:05"
      }
    },
  ],
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectTitleHistoryUnRead()
