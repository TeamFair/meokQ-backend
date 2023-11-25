package com.meokq.api.application.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

@Schema(name = "Market-Request")
class MarketReq(
    @Schema(description = "logo 이미지 Id")
    val logoImageId : String? = null,

    @Schema(description = "가게명(점포명)")
    val name : String,

    @Schema(description = "가게(점포) 연락처")
    val phone : String,

    @Schema(description = "법정동 코드")
    val district : String,

    @Schema(description = "주소")
    val address : String,

    @Schema(description = "영업시간")
    @field:Valid
    val marketTime : List<MarketTimeReq>,

    @Schema(description = "대표 관리자 Id")
    val presidentId : String
)