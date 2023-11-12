package com.meokq.api.application.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Market-Detail-Response")
class MarketDetailResp (
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

    @Schema(description = "사용가능한 미션 개수")
    val ticketCount : Int?,

    @Schema(description = "영업시간")
    var marketTime : List<MarketTimeResp>? = mutableListOf()
)