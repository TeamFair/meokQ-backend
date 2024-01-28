package com.meokq.api.coupon.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICP001) 쿠폰 목록 조회",
    description = """
            사용자앱에서 호출하는 경우, userDataOnly가 true여야 합니다.(소유한 쿠폰만 조회할 수 있습니다.)
            보스앱에서 호출하는 경우, marketId가 null이 유효한 값이어야 합니다.(해당 마켓에서 발급한 쿠폰만 조회할 수 있습니다.)
        """,
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
                      "useDate": "2024-01-21 23:35:38",
                      "status": "USED",
                      "couponId": "CP10000003",
                      "expireDate": null,
                      "marketId": "MK00000001",
                      "userNickname": "nickname1",
                      "reward": {
                        "rewardId": "RW00000002",
                        "content": null,
                        "target": "DONUT",
                        "quantity": 1,
                        "discountRate": null,
                        "type": "GIFT"
                      },
                      "missions": [
                        {
                          "content": null,
                          "target": "COFFEE",
                          "quantity": 3,
                          "type": "NORMAL"
                        }
                      ]
                    }
                  ],
                  "total": 1,
                  "page": 0,
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainSelectCouponList()
