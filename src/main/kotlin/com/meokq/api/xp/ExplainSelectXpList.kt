package com.meokq.api.xp

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IXP001) 경험치 로그 조회",
    description = "사용자의 경험치 기록을 조회한다.",
    //tags = ["Market"]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
                """)])]
)
annotation class ExplainSelectXpList()
