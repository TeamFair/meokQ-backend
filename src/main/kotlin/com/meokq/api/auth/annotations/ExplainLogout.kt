package com.meokq.api.auth.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IAC002) logout",
    description = "logout",
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
annotation class ExplainLogout