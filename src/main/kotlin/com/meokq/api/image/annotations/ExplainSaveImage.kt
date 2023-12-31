package com.meokq.api.image.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IIM001) 이미지 단건 등록",
    description = "이미지를 등록합니다. S3연결후 location이 사용 가능한 형태로 변경될 예정입니다. 응답 예시를 확인해주세요."
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
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
annotation class ExplainSaveImage
