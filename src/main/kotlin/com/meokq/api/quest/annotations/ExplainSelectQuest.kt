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
        FREE -> return "{mission.content}"
        NORMAL -> return "{mission.target} {mission.quantity}개(잔) 주문"
        
        2. rewardTitle
        GIFT -> return "{reward.target} {reward.quantity} 증정권"
        DISCOUNT -> return "{reward.target} {reward.discountRate}% 할인권"
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
{
  "data": {
    "questId": "QS00000001",
    "marketId": "MK00000001",
    "missionTitles": [
      "TEA 5개(잔) 주문"
    ],
    "rewardTitles": [
      "COFFEE 80% 할인권"
    ],
    "status": "UNDER_REVIEW"
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectQuest()
