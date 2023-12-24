package com.meokq.api.market.reposone

import com.meokq.api.market.enums.MarketAuthResult
import com.meokq.api.market.model.MarketAuth
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Market-Auth-Response",
    description = "사업자 및 개인정보"
)
//@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarketAuthResp (
    @Schema(description = "인증내역 Id")
    val recordId : String?,

    @Schema(description = "마켓 Id")
    val marketId : String?,

    @Schema(description = "검토결과")
    val reviewResult: MarketAuthResult?,

    @Schema(description = "검토의견")
    val comment: String?,

    @Schema(description = "대표자 개인정보")
    val operator : OperatorAuthResp?,

    @Schema(description = "영업신고증 정보")
    val license: LicenseAuthResp?,
){
    constructor(model : MarketAuth) : this(
        recordId = model.recordId,
        marketId = model.marketId,
        reviewResult = model.reviewResult,
        comment = model.comment,
        operator = model.operatorAuth?.let { OperatorAuthResp(it) },
        license = model.licenseAuth?.let { LicenseAuthResp(it) },
    )
}