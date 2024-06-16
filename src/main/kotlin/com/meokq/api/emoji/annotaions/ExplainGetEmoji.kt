package com.meokq.api.emoji.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IEJ002) 이모지 조회",
    description = "해당 유저가 타겟의 이모지 생성 여부를 조회 합니다."
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(
            value = """
{
  "data": {
    "isLike": "true",
    "isHate": "true"
   },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """
        )]
    )]
)
annotation class ExplainGetEmoji()
