package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU015) Quest 상세정보 조회",
    description = """
        Quest 상세정보를 조회합니다.
    """,
    parameters = [
        Parameter(name = "questId", description = "questId", required = true, example = "1caabdcd-55aa-480c-9c0b-070f46e8b2fc"),
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
    "questId": "ff790fb0-b241-42c5-b289-d609deecacb3",
    "marketId": null,
    "missionTitles": [
      "라떼 마시기"
    ],
    "rewardTitles": [
      "30xp 경험치 부여",
      "50xp 경험치 부여"
    ],
    "status": "PUBLISHED",
    "expiredData": "2030-12-31T00:00:00",
    "imageId": "IMQU2024092501012627",
    "score": 0,
    "type": "NORMAL",
    "target": "NONE",
    "mainImageId": "",
    "popularYn": false
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectCustomerQuest()
