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
       XP -> return "{reward.quantity}xp 경험치 부여"
        
        2. rewardTitle
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
  "size": 10,
  "data": [
    {
      "questId": "db1e6701-35f5-45c3-abda-527b1989dce2",
      "marketId": null,
      "writer": "일상",
      "missionTitle": "우선순위2",
      "status": "PUBLISHED",
      "expireDate": "2030-12-31T00:00:00",
      "creatorRole": "ADMIN",
      "imageId": "IMMA2024072114492808",
      "score": 2,
      "rewardList": [
        {
          "rewardId": "3ab053d4-6630-42ce-ad1d-d2415fb403c8",
          "content": "STRENGTH",
          "target": null,
          "quantity": 20,
          "discountRate": null,
          "type": "XP",
          "title": null,
          "questId": "db1e6701-35f5-45c3-abda-527b1989dce2"
        },
        {
          "rewardId": "5ee04fac-6e24-44b2-918b-9fd78fc5fe83",
          "content": "CHARM",
          "target": null,
          "quantity": 20,
          "discountRate": null,
          "type": "XP",
          "title": null,
          "questId": "db1e6701-35f5-45c3-abda-527b1989dce2"
        },
        {
          "rewardId": "92109836-26b8-4c08-b6b0-7e629ca2a623",
          "content": "SOCIABILITY",
          "target": null,
          "quantity": 20,
          "discountRate": null,
          "type": "XP",
          "title": null,
          "questId": "db1e6701-35f5-45c3-abda-527b1989dce2"
        },
        {
          "rewardId": "bb9f958b-380e-4918-9b09-702adc5451f2",
          "content": "INTELLECT",
          "target": null,
          "quantity": 20,
          "discountRate": null,
          "type": "XP",
          "title": null,
          "questId": "db1e6701-35f5-45c3-abda-527b1989dce2"
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
