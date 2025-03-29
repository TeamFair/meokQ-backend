package com.meokq.api.quest.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQF002) Quest 즐겨찾기 삭제",
    description = """
        Quest 즐겨찾기를 삭제합니다.
    """
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
//        schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
                {
                  "data": {
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ExplainQuestFavoriteDeleteQuest
