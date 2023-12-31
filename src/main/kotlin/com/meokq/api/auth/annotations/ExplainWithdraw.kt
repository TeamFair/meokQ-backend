package com.meokq.api.auth.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IAC003) withdraw",
    description = "회원탈퇴 : 인증정보 및 회원 정보 삭제",
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        // TODO : Fill
        examples = [ExampleObject(value = """
            """)]
    )]
)
annotation class ExplainWithdraw
