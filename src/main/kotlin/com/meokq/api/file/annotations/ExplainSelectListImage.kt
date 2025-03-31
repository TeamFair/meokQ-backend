package com.meokq.api.file.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IIM004) 이미지 리스트 조회",
    description = "이미지 전체 정보를 조회합니다.",
    parameters = [
        Parameter(name = "type", description = "이미지 타입", required = true, example = "QUEST_IMAGE"),
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
                {
                  "data": [
                    {
                      "imageId": "IMMA2024031114590814",
                      "location": "/open/images/IMMA2024031114590814"
                    }
                  ],
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainSelectListImage()
