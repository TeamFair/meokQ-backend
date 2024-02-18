package com.meokq.api.market.annotations

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "영업시간",
    example = """
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
annotation class ExplainMarketTimeListSchema()
