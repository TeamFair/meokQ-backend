package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU004) Quest 상세정보 조회",
    description = """
        Quest 상세정보를 조회합니다.
        
        1. missionTitle
        Free -> "라떼 마시기"
        
        2. rewardTitle
        XP -> return "{reward.quantity}xp 경험치 부여"
    """,
    parameters = [
        Parameter(name = "questId", description = "questId", required = true, example = "QS00000001"),
        Parameter(name = "page", description = "페이지 번호", required = false),
        Parameter(name = "size", description = "페이지 크기", required = false)
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
    "score": 0
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectQuest()
