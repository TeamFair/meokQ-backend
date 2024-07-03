package com.meokq.api.emoji.annotaions

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IEJ001) 이모지 등록",
    description = "이모지를 등록 합니다."
)

@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
            {
              "data": {
                "emojiId" : "IEJ1000001",
                "emojiStatus" : "LIKE",
                "targetId" :"CH10000001",
                "targetType" : "CHALLENGE"
                },
              "status": "OK",
              "message": "Your request has been processed successfully."
            }
                """
        )]
    )]
)
annotation class ExplainSaveEmoji()
