package com.meokq.api.market.request

import com.meokq.api.market.enums.MarketStatus
import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "Market-Search-Dto")
class MarketSearchDto(
    @Schema(description = "법정동 코드", example = "1100000000")
    val district : String? = null,
    @Schema(description = "BOSS(대표관리자) ID", example = "BS10000001")
    var presidentId : String? = null,
    @Schema(description = "marketId", example = "MK00000001")
    val marketId : String? = null,
    @Schema(description = "market 상태값", example = "APPROVED")
    var status: MarketStatus? = null,

    // TODO : 확인필요.
    @Schema(description = "소유한 마켓")
    var ownMarketOnly : Boolean? = null,
)