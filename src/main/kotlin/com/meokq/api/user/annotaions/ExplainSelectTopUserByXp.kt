package com.meokq.api.user.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IUS004) xp의 sum 기준으로 Top users 조회",
    description = "customer 정보를 xp의 sum 기준으로 조회한다."
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
      "nickname": "채식",
      "xpSum": 2540,
      "lank": 1
    },
    {
      "nickname": "뽀야",
      "xpSum": 2430,
      "lank": 2
    },
    {
      "nickname": "Marciana",
      "xpSum": 1980,
      "lank": 3
    },
    {
      "nickname": "실로",
      "xpSum": 1760,
      "lank": 4
    },
    {
      "nickname": "사이즈",
      "xpSum": 1660,
      "lank": 5
    },
    {
      "nickname": "라비",
      "xpSum": 1440,
      "lank": 6
    },
    {
      "nickname": "귀랑",
      "xpSum": 1290,
      "lank": 7
    },
    {
      "nickname": "소상우사",
      "xpSum": 1200,
      "lank": 8
    },
    {
      "nickname": "USER2024020513113554",
      "xpSum": 1090,
      "lank": 9
    },
    {
      "nickname": "소프티모",
      "xpSum": 1040,
      "lank": 10
    }
  ],
  "total": 10,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectTopUserByXp()
