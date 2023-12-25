package com.meokq.api.ticket

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK013) 마켓의 티켓거래 내역을 조회합니다.",
    description = "티켓의 거래내역을 조회합니다. 추후 개발 예정입니다.",
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
annotation class ExplainSelectTicketHis()
