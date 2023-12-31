package com.meokq.api.challenge.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICH003) 도전내역 조회",
    description = "마켓에서 등록한 진행상태별 도전내역을 조회합니다.",
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
                  "challengeId": "CH10000004",
                  "quest": {
                    "questId": "QS10000002",
                    "marketId": "MK00000002",
                    "missions": [
                      {
                        "content": null,
                        "target": "COFFEE",
                        "quantity": 3,
                        "type": "NORMAL"
                      }
                    ],
                    "rewards": [
                      {
                        "content": null,
                        "target": "DONUT",
                        "quantity": 1,
                        "discountRate": null,
                        "type": "GIFT"
                      }
                    ]
                  },
                  "receiptImageId": "IM10000003",
                  "status": "UNDER_REVIEW"
                },
                {
                  "challengeId": "CH10000002",
                  "quest": {
                    "questId": "QS10000002",
                    "marketId": "MK00000002",
                    "missions": [
                      {
                        "content": null,
                        "target": "COFFEE",
                        "quantity": 3,
                        "type": "NORMAL"
                      }
                    ],
                    "rewards": [
                      {
                        "content": null,
                        "target": "DONUT",
                        "quantity": 1,
                        "discountRate": null,
                        "type": "GIFT"
                      }
                    ]
                  },
                  "receiptImageId": "IM10000002",
                  "status": "UNDER_REVIEW"
                }
              ],
              "total": 2,
              "page": 0,
              "status": "OK",
              "message": "Your request has been processed successfully."
            }
        """)])]
)
annotation class ExplainSelectChallengeList
