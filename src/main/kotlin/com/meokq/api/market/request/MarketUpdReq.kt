package com.meokq.api.market.request

import com.meokq.api.market.annotations.ExplainMarketTimeListSchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

class MarketUpdReq(
    @Schema(description = "logo 이미지 Id", example = "IM10000003")
    val logoImageId : String?,

    @Schema(description = "주소", example = "서울특별시 종로구 신교동")
    val address : String?,

    @ExplainMarketTimeListSchema
    @field:Valid
    val marketTime : List<MarketTimeReq>,

    @Schema(description = "가게(점포) 연락처", example = "0211111111")
    val phone : String?,
)