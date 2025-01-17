package com.meokq.api.banner.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IBN002) 배너 목록 조회",
    description = "제공된 필터 조건에 따라 배너 목록을 조회합니다.",
    parameters = [
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공적으로 배너 목록을 조회함",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
                {
  "size": 10,
  "data": [
    {
      "id": 1,
      "title": "카페광고 Banner",
      "description": "카페광고 관련 배너입니다.",
      "image": {
        "imageId": "IMG001",
        "location": "/open/images/IMG001"
      },
      "activeYn": "Y"
    },
    {
      "id": 2,
      "title": "설날 이벤트 Banner",
      "description": "설날 이벤트 관련 배너입니다.",
      "image": {
        "imageId": "IMG002",
        "location": "/open/images/IMG002"
      },
      "activeYn": "Y"
    }
  ],
  "total": 2,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)]
    )]
)
annotation class ExplainSelectBanner()