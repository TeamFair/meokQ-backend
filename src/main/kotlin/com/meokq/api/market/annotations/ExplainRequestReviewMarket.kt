package com.meokq.api.market.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK014) 마켓인증정보 검토 요청",
    description = "BOSS 타입의 사용자가 마켓인증 정보 검토를 요청한다.",
    //tags = ["Market"],
    parameters = [
        Parameter(name = "marketId", description = "marketId", required = true, example = "MK00000010")
    ]
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
annotation class ExplainRequestReviewMarket()
