package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse


@Operation(
    summary = "(IQU005) quest 게시 요청",
    description = "퀘스트 상태를 게시중으로 변경합니다.",
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
@ApiResponse(
    responseCode = "500",
    description = "상태값 오류",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
            {
              "errMessage": "이미 게시중인 퀘스트입니다.",
              "status": "INTERNAL_SERVER_ERROR",
              "message": "An unknown error occurred."
            }
        """)]
    )]
)
annotation class ExplainUpdateQuestPublish()
