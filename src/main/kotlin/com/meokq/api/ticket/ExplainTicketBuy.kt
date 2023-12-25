package com.meokq.api.ticket

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK011) 마켓의 티켓구매요청",
    description = "티켓의 구매를 요청합니다. 추후 개발 예정입니다. (이용권 구매하기 버튼 선택시 호출해주세요.)",
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
annotation class ExplainTicketBuy {
}