package com.meokq.api.application.request

class MarketRequest(
    val logoImage : String?,
    val name : String?,
    val phone : String?,
    val district : String?,
    val address : String?,
    val marketTime : List<MarketTimeRequest>?
)