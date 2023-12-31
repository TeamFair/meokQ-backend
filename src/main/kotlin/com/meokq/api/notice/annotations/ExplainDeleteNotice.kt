package com.meokq.api.notice.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(INT003) notice 정보 삭제",
    description = "notice 정보를 삭제합니다.",
    parameters = [
        Parameter(name = "noticeId", description = "notice 아이디", required = true, example = "NT00000001"),
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        //schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
                """)])]
)
annotation class ExplainDeleteNotice
