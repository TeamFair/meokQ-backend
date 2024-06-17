package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU001) Quest 목록 조회",
    description = """
        조건에 맞는 모든 Quest 목록을 조회합니다.
        
        1. missionTitle
        FREE -> return "{mission.content}"
        NORMAL -> return "{mission.target} {mission.quantity}개(잔) 주문"
        XP -> return "{reward.quantity}xp 경험치 부여"
        
        2. rewardTitle
        GIFT -> return "{reward.target} {reward.quantity} 증정권"
        DISCOUNT -> return "{reward.target} {reward.discountRate}% 할인권"
        XP -> return "{reward.quantity}xp 경험치 부여"
    """,
    parameters = [
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
  "size": 2,
  "data": [
    {
      "questId": "QS00000001",
      "marketId": "MK00000001",
      "missionTitle": "TEA 5개(잔) 주문",
      "rewardTitle": "COFFEE 80% 할인권",
      "status": "PUBLISHED",
      "expireDate": "2024-06-02 18:43:11"
    },
    {
      "questId": "QS00000002",
      "marketId": "MK00000001",
      "missionTitle": "TEA 5개(잔) 주문",
      "rewardTitle": "COFFEE 90% 할인권",
      "status": "UNDER_REVIEW",
      "expireDate": null
    }
  ],
  "total": 2,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainSelectQuestList()
