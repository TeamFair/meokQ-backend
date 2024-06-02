package com.meokq.api.emoji.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(EJS001) 좋아요 이모지 등록",
    description = "좋아요 이모지를 등록 합니다."
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "data": {
    "status": "LIKE",
    "emojiId": "EJ10000001"
   },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSaveLikeEmoji()
