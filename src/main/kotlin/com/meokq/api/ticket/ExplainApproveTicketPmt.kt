package com.meokq.api.ticket

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK012) 마켓의 티켓구매내역을 승인합니다.",
    description =
            "결제가 완료되었는지 확인하고, 마켓에 티켓을 부여합니다. (관리자 앱에서 호출해주세요.) " +
            "추후 개발 예정입니다.",
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
annotation class ExplainApproveTicketPmt {
}