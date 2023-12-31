package com.meokq.api.market.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK001) 마켓정보 조회",
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
        schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
{
  "size": 10,
  "data": [
    {
      "marketId": "MK00000001",
      "logoImage": {
        "imageId": "IM10000003",
        "location": "/path/to/image3"
      },
      "name": "Market Name",
      "district": "1100000000",
      "address": "서울특별시 종로구",
      "status": "APPROVED",
      "questCount": 1,
      "marketTime": {
        "weekDay": "SUN",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "Y"
      }
    }
  ],
  "total": 1,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectMarketList
