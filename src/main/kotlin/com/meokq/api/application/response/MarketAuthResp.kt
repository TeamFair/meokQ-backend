package com.meokq.api.application.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.meokq.api.application.enums.ReviewResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Market-Auth-Response",
    description = "사업자 및 개인정보"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarketAuthResp (
    @Schema(description = "인증내역 Id")
    val recordId : String?,

    @Schema(description = "마켓 Id")
    val marketId : String?,

    @Schema(description = "검토결과")
    val reviewResult: ReviewResult?,

    @Schema(description = "검토의견")
    val comment: String?,

    @Schema(description = "대표자 개인정보")
    val owner : OwnerAuthResp?,

    @Schema(description = "영업신고증 정보")
    val license: LicenseAuthResp?,
)