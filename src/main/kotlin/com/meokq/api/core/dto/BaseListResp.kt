package com.meokq.api.core.dto

import com.meokq.api.application.enums.ErrorStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "공통 리스트 응답")
class BaseListResp<T>(
    content : MutableList<T>,
    totalElements: Long,
    size: Int,
    number: Int,
    errorStatus: ErrorStatus = ErrorStatus.OK
) {
    @Schema(description = "데이터")
    val data: MutableList<T> = content

    @Schema(description = "전체 항목 수")
    val total: Long = totalElements

    @Schema(description = "페이지당 항목 수")
    val size: Int = size

    @Schema(description = "현재 페이지")
    val page: Int = number

    @Schema(description = "응답 상태")
    var status: String? = errorStatus.name

    @Schema(description = "응답 메시지")
    var message: String? = errorStatus.message
}