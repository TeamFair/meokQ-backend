package com.meokq.api.market.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK007) 마켓인증 검토결과 등록",
    description = "ADMIN 타입의 사용자가 마켓인증 검토결과를 등록합니다.",
    tags = ["Market"],
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "data": {},
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainReviewMarketAuth()
