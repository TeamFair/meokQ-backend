package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH004) 도전내역 랜덤 조회",
    description = "모든 도전내역을 랜덤 알고리즘을 통해 조회합니다.",
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
      "challengeId": "CH10000005",
      "userNickName": "nickname1",
      "quest": {
        "questId": "QS10000002",
        "marketId": "MK00000001",
        "missions": [
          {
            "content": null,
            "target": "COFFEE",
            "quantity": 3,
            "type": "NORMAL",
            "title": "COFFEE 3개(잔) 주문"
          }
        ],
        "rewards": [
          {
            "rewardId": "RW00000002",
            "content": null,
            "target": "DONUT",
            "quantity": 1,
            "discountRate": null,
            "type": "GIFT",
            "title": "DONUT 1개(잔) 증정권"
          }
        ]
      },
      "receiptImageId": "IM10000002",
      "status": "APPROVED"
    },
    {
      "challengeId": "CH10000004",
      "userNickName": "nickname3",
      "quest": {
        "questId": "QS10000002",
        "marketId": "MK00000001",
        "missions": [
          {
            "content": null,
            "target": "COFFEE",
            "quantity": 3,
            "type": "NORMAL",
            "title": "COFFEE 3개(잔) 주문"
          }
        ],
        "rewards": [
          {
            "rewardId": "RW00000002",
            "content": null,
            "target": "DONUT",
            "quantity": 1,
            "discountRate": null,
            "type": "GIFT",
            "title": "DONUT 1개(잔) 증정권"
          }
        ]
      },
      "receiptImageId": "IM10000003",
      "status": "APPROVED"
    }
  ],
  "total": 5,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
        """)])]
)
annotation class ExplainRandomSelectChallengeList
