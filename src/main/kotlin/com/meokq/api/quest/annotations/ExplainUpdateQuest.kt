package com.meokq.api.quest.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU010) Quest 정보 변경",
    description = """
        Quest의 정보를 변경 합니다.
        {
          "writer": "일상테스트유저",
          "imageId": "string",
          "missions": [
            {
              "content": "소나무 사진찍기",
              "target": "string",
              "quantity": 0,
              "type": "FREE"
            }
          ],
          "rewards": [
            {
              "content": "STRENGTH",
              "target": "쿠키",
              "quantity": 50,
              "discountRate": 0,
              "type": "XP"
            }
          ],
          "expireDate": "2024-12-30"
        }
        """,
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
                {
                  "data": {
                    "questId": "QS10000002"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExplainUpdateQuest