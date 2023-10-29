package com.meokq.api.application.response

class MarketDetailResponse (
    val marketId : String?,
    val logoImage : String?,
    val name : String?,
    val phone : String?,
    val district : String?,
    val address : String?,
    val ticketCount : Int?,
    // TODO : 영업시간 추가
)