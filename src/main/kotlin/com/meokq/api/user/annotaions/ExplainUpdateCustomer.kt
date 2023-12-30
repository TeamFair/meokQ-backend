package com.meokq.api.user.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IUS002) customer 정보 수정",
    description = "customer 정보 수정"
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
@ApiResponse(
    responseCode = "500",
    description = "중복된 닉네임이 존재할때 응답",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "errMessage": "nickname : nickname123 is not unique.",
  "status": "INTERNAL_SERVER_ERROR",
  "message": "An unknown error occurred."
}
                """)])]
)
annotation class ExplainUpdateCustomer()
