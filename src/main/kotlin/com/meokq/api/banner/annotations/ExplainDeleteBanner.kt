package com.meokq.api.banner.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IBN004) 배너 삭제",
    description = "배너 ID를 통해 기존 배너 정보를 삭제합니다. 배너와 연결된 이미지도 함께 삭제됩니다.",
    parameters = [
        Parameter(name = "bannerId", description = "삭제할 배너 ID", required = true, example = "1")
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "배너 정보가 성공적으로 삭제됨",
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
annotation class ExplainDeleteBanner