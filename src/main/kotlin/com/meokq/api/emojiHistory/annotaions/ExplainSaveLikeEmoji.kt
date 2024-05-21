package com.meokq.api.emojiHistory.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IUS001) 좋아요 이모지 등록",
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
    "emojiId": "Emoji202312301101031367",
    "createDate": "2023-12-30 11:01:03.1367"
   },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSaveLikeEmoji()
