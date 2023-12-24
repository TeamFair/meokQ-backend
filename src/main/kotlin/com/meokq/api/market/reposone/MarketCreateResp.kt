package com.meokq.api.market.reposone

import io.swagger.v3.oas.annotations.media.Schema

class MarketCreateResp(
    @Schema(description = "마켓 아이디")
    val marketId : String?,
)