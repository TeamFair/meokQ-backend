package com.meokq.api.application.response

import com.meokq.api.application.enums.WeekDay

data class MarketTimeResponse(
    var weekDay: WeekDay?,
    var openTime: String?,
    var closeTime: String?
)
