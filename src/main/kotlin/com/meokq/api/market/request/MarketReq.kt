package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

@Schema(name = "Market-Request")
class MarketReq(
    @Schema(description = "logo 이미지 Id", example = "IM10000003")
    val logoImageId : String? = null,

    @Schema(description = "가게명(점포명)", example = "점포명")
    val name : String,

    @Schema(description = "가게(점포) 연락처", example = "0211111111")
    val phone : String,

    @Schema(description = "법정동 코드", example = "1111010200")
    val district : String,

    @Schema(description = "주소", example = "서울특별시 종로구 신교동")
    val address : String,

    @Schema(description = "영업시간", example = """
    [
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
    """)
    @field:Valid
    val marketTime : List<MarketTimeReq>
)