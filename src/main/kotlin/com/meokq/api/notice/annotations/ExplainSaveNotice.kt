package com.meokq.api.notice.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(INT002) 공지사항 등록",
    description = "공지사항을 등록합니다."
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        //schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
{
  "data": {
    "noticeId": "35bc8ab7-d55a-4f97-b174-bfcbdda29365",
    "title": "공지사항 제목",
    "content": "공지사항 테스트입니다.",
    "createDate": "2023-12-25 02:14:24",
    "target": "ALL"
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSaveNotice
