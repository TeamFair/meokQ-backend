package com.meokq.api.xp.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IXP001) 경험치 로그 조회",
    description = "사용자의 경험치 기록을 조회한다.",
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
      "recordId": 2,
      "title": "아메리카노 2잔 구매",
      "xpPoint": 100,
      "createDate": "2024-07-07 23:14:25"
    },
    {
      "recordId": 1,
      "title": "범죄도시 영화보기",
      "xpPoint": 100,
      "createDate": "2024-07-07 23:14:25"
    }
  ],
  "total": 2,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectXpList()
