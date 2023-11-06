package com.meokq.api.application.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Market-Response")
class MarketResponse(
    @Schema(description = "마켓 아이디")
    val marketId : String?,

    @Schema(description = "logo 이미지 주소")
    val logoImage : String?,

    @Schema(description = "가게명(점포명)")
    val name : String?,

    @Schema(description = "가게(점포) 연락처")
    val phone : String?,

    @Schema(description = "법정동 코드")
    val district : String?,

    @Schema(description = "주소")
    val address : String?,
)