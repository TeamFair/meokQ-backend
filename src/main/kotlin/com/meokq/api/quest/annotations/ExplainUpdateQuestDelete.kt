package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQU003) quest 삭제 요청",
    description = "퀘스트 상태를 삭제로 변경합니다.",
    tags = ["Quest"],
    parameters = [
        Parameter(
            name = "questId",
            description = "quest 아이디",
            required = true,
            example = "QS10000001"
        ),
    ],
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        //schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
            {
              "data": {
                "questId": "QS00000002",
                "marketId": "MK00000001"
              },
              "status": "OK",
              "message": "Your request has been processed successfully."
            }
            """)])]
)
annotation class ExplainUpdateQuestDelete()
