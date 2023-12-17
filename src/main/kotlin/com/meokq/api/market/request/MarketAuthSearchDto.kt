package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "MarketAuth-Search-Dto")
class MarketAuthSearchDto(
    @Schema(description = "marketId")
    val marketId : String? = null,
)