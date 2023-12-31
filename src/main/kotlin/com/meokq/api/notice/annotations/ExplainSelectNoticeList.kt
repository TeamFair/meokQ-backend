package com.meokq.api.notice.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(INT001) 공지사항 조회",
    description = """
            사용자의 타입에 따라 조회할 수 있는 유형이 정해집니다.
            UserType.BOSS -> NoticeTarget.BOSS
            UserType.CUSTOMER -> NoticeTarget.CUSTOMER
            UserType.UNKNOWN -> NoticeTarget.ALL
        """,
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
        //schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
{
  "size": 10,
  "data": [
    {
      "noticeId": "NT00000004",
      "title": "General Notice",
      "content": "Content for everyone",
      "createDate": null,
      "target": "ALL"
    }
  ],
  "total": 1,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectNoticeList
