package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Market-Auth-Request",
    description = "사업자 및 개인정보"
)
data class MarketAuthReq(
    @Schema(description = "마켓 Id")
    val marketId : String?,

    @Schema(description = "대표자 개인정보")
    val owner : OwnerAuthReq,

    @Schema(description = "영업신고증 정보")
    val license: LicenseAuthReq,
)