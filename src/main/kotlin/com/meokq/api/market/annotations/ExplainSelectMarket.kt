package com.meokq.api.market.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK006) 마켓정보 세부정보 조회",
    parameters = [
        Parameter(name = "marketId", description = "마켓 아이디", required = true, example = "MK00000001"),
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
  "data": {
    "marketId": "MK00000001",
    "name": "Market Name",
    "phone": "0211111111",
    "district": "1100000000",
    "address": "서울특별시 종로구",
    "status": "APPROVED",
    "ticketCount": 10,
    "logoImageId" : "image-id",
    "marketTimes": [
      {
        "weekDay": "FRI",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "MON",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "SAT",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "SUN",
        "openTime": "09:00",
        "closeTime": "20:00",
        "holidayYn": "Y"
      },
      {
        "weekDay": "THU",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "TUE",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      },
      {
        "weekDay": "WED",
        "openTime": "09:00",
        "closeTime": "18:00",
        "holidayYn": "N"
      }
    ]
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectMarket
