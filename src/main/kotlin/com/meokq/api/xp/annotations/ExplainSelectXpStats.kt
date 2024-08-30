package com.meokq.api.xp.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IXP002) 스텟 조회",
    description = "사용자의 스텟을 조회합니다.",
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "data": {
    "strength_stat": 0,
    "intellect_stat": 0,
    "fun_stat": 0,
    "charm_stat": 0,
    "sociability_stat": 50
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectXpStats()
