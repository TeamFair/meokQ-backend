package com.meokq.api.title.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ITH002) Title History 칭호별 랭크 조회",
    description = """
        칭호별 사용자 순위를 조회합니다.
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
      "customer": {
        "status": "ACTIVE",
        "nickname": "아아",
        "couponCount": null,
        "completeChallengeCount": null,
        "xpPoint": null,
        "profileImage": null,
        "title": {
          "id": "TQ00030",
          "name": "🛠️ 퀘스트 대장장이 🛠️",
          "type": "LEGEND",
          "createdAt": "2025-05-03T17:19:05"
        }
      },
      "titleHistory": {
        "id": "b53b11bd-a2eb-4035-a72b-675685a6a938",
        "createdAt": "2025-05-03T23:05:49"
      }
    }
  ],
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectTitleHistoryRank()
