package com.meokq.api.quest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse


@Operation(
    summary = "(IQU006) 완료한 quest 목록 조회",
    description = "완료한 퀘스트 목록을 조회 합니다.",
    tags = ["Quest"],
    parameters = [
        Parameter(name = "page", description = "페이지 번호", required = false),
        Parameter(name = "size", description = "페이지 크기", required = false)
    ],
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        examples = [ExampleObject(value = """
{
  "size": 2,
  "data": [
    {
      "questId": "QS00000001",
      "marketId": "MK00000001",
      "missionTitle": "TEA 5개(잔) 주문",
      "rewardTitle": "COFFEE 80% 할인권",
      "status": "PUBLISHED",
      "expireDate": "2024-06-02 18:43:11"
    },
    {
      "questId": "QS00000002",
      "marketId": "MK00000001",
      "missionTitle": "범죄도시4 보기",
      "rewardTitle": "COFFEE 90% 할인권",
      "status": "PUBLISHED",
      "expireDate": null
    }
  ],
  "total": 2,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
            """)])]
)
annotation class ExplainCompletedQuests()
