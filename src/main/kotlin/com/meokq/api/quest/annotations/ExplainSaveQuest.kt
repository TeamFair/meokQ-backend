package com.meokq.api.quest.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU002) Quest 등록",
    description = """
        새로운 Quest를 저장합니다.
        
        1. mission
        XP 형태인 경우 아래처럼 채워주시면 됩니다.
        {
          "content": "라떼 마시기", # 미션 Title
          "target": "",
          "quantity": 0,
          "type": "FREE"
        }
        
        1. reward
        증정권인 경우 아래처럼 채워주시면 됩니다.
            {
              "content": "INTELLECT", # XP Type
              "target": "", 
              "quantity": 1, # XP 증가량
              "discountRate": 0,
              "type": "XP"
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
annotation class ExplainSaveQuest