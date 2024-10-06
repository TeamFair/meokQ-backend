package com.meokq.api.user.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IUS003) customer Xp 랭크 조회",
    description = "customer Xp 랭크 조회"
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "data": [
        {
          "xpType": "CHARM",
          "xpPoint": 200,
          "customerId": "4e7b25a2-5c65-4de4-82f7-f0034f5d4615",
          "nickname": "USER2024020815103834"
        },
        {
          "xpType": "CHARM",
          "xpPoint": 120,
          "customerId": "82eb81c2-7df9-4e47-9362-c71c6ac78f60",
          "nickname": "USER2024021414331002"
        },
        {
          "xpType": "CHARM",
          "xpPoint": 100,
          "customerId": "feeb066f-a118-4dfd-a141-eb8d6f31b8b1",
          "nickname": "USER2024020513113554"
        },
        {
          "xpType": "CHARM",
          "xpPoint": 90,
          "customerId": "CS10000002",
          "nickname": "USER2024020513222222"
        },
        {
          "xpType": "CHARM",
          "xpPoint": 0,
          "customerId": "CS10000001",
          "nickname": "USER2024020513113551"
        }
      ],
          "status": "OK",
          "message": "Your request has been processed successfully."
      
      }
                """)]
    )]
)
annotation class ExplainGetRankForXpCustomer()
