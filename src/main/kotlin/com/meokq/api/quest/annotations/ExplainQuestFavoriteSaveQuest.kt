package com.meokq.api.quest.annotations

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IQF001) Quest 즐겨찾기 등록",
    description = """
        Quest 즐겨찾기를 저장합니다.
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
annotation class ExplainQuestFavoriteSaveQuest
