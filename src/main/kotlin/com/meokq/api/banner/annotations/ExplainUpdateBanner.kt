package com.meokq.api.banner.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse


@Operation(
    summary = "(IBN003) 배너정보 수정",
    description = "배너 ID를 통해 기존 배너의 정보를 수정합니다.",
    parameters = [
        Parameter(name = "bannerId", description = "수정할 배너 ID", required = true, example = "1"),
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "배너 정보가 성공적으로 수정됨",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "data": {},
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)]
    )]
)
annotation class ExplainUpdateBanner()
