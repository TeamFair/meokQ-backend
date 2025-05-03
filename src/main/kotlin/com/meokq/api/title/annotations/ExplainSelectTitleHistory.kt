package com.meokq.api.title.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ITH001) Title History 전체 조회 - 전체 칭호 포함",
    description = """
        전체 칭호 중 획득한 칭호를 조회합니다.
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
      "titleHistory": null,
      "title": {
        "id": "TQ00025"
        "name": "천재적인 사고력",
        "type": "STANDARD",
        "createdAt": "2025-05-03T17:19:05"
      }
    },
    {
      "titleHistory": null,
      "title": {
        "id": "TQ00026"
        "name": "세상을 읽는 자",
        "type": "RARE",
        "createdAt": "2025-05-03T17:19:05"
      }
    }
  ],
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectTitleHistory()
