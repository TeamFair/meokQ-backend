package com.meokq.api.auth.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IAC001) login",
    description = "로그인 또는 회원가입",
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
annotation class ExplainLogin
