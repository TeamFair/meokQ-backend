package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse


@Operation(
    summary = "(IQU008) Quest 삭제",
    description = "퀘스트를 삭제 합니다.",
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
                "questId": "QS00000002"
              },
              "status": "OK",
              "message": "Your request has been processed successfully."
            }
            """)])]
)
@ApiResponse(
    responseCode = "500",
    description = "상태값 오류",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
            {
              "errMessage": "삭제 완료된 퀘스트입니다.",
              "status": "INTERNAL_SERVER_ERROR",
              "message": "An unknown error occurred."
            }
        """)]
    )]
)
annotation class ExplainDeleteQuest()
