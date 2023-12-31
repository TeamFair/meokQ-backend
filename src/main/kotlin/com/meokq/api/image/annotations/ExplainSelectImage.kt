package com.meokq.api.image.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IIM002) 이미지 정보 조회",
    description = "이미지 정보를 조회합니다.",
    parameters = [
        Parameter(name = "imageId", description = "이미지 아이디", required = true, example = "IM10000004"),
    ]
)
@ApiResponse(
    responseCode = "500",
    description = "잘못된 요청",
    content = [Content(
        mediaType = "application/json",
        schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
                {
                  "errMessage": "UNKNOWN의 사용자는 BUSINESS_REGISTRATION_CERTIFICATE타입의 이미지를 조회할 수 없습니다.\n(이미지 조회가 가능한 사용자 : [BOSS])",
                  "status": "INTERNAL_SERVER_ERROR",
                  "message": "An unknown error occurred."
                }
            """)])]
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
                    "imageId": "IM2023122418180655",
                    "location": "https://ifh.cc/v-pORvTP"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
annotation class ExplainSelectImage
