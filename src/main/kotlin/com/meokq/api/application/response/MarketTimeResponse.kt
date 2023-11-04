package com.meokq.api.application.response

import com.meokq.api.application.enums.WeekDay
import java.time.LocalTime

data class MarketTimeResponse(
    var weekDay: WeekDay?,
    var openTime: LocalTime?,
    var closeTime: LocalTime?
)
