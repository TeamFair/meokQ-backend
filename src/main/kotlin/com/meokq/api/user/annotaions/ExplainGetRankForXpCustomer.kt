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
          "xpType": "STRENGTH",
          "xpPoint": 150,
          "xpTotalPoint": 530,
          "title": null,
          "customerId": "53da0082-2d7b-4a33-8719-1e713f10fae2",
          "nickname": "일상122",
          "profileImage": null
        }
      ],
          "status": "OK",
          "message": "Your request has been processed successfully."
      
      }
                """)]
    )]
)
annotation class ExplainGetRankForXpCustomer()
