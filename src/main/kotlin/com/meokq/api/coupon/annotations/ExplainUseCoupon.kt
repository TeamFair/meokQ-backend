package com.meokq.api.coupon.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(ICP002) 쿠폰 사용",
    description = "쿠폰을 사용합니다.",
    parameters = [
        Parameter(
            name = "couponId",
            description = "coupon 아이디",
            required = true,
            example = "CP10000001"
        ),
    ],
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
annotation class ExplainUseCoupon()
