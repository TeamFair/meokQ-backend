package com.meokq.api.market.request

import com.meokq.api.market.enums.MarketAuthResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Market-Auth-Review-Request", description = "마켓인증내역 검토결과")
class MarketAuthReviewReq(
    @Schema(description = "인증내역 식별자", example = "MA00000001")
    val recordId : String,

    @Schema(description = "검토의견", example = "영업신고증을 다시 확인해주세요.")
    val comment : String? = null,

    @Schema(description = "검토결과", example = "REJECTED")
    val reviewResult : MarketAuthResult
)