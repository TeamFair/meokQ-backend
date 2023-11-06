package com.meokq.api.application.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.meokq.api.application.enums.ErrorStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "공통 단건 응답")
@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse(
    data : Any?,
    errorStatus : ErrorStatus,
    message : String = errorStatus.message
) {
    @Schema(description = "데이터")
    var data : Any? = data

    @Schema(description = "응답 상태")
    var status : String = errorStatus.name

    @Schema(description = "응답 메시지")
    var message : String = message
}