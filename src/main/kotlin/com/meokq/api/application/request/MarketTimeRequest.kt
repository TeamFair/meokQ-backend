package com.meokq.api.application.request

import com.meokq.api.application.enums.WeekDay
import java.time.LocalTime

data class MarketTimeRequest(
    var weekDay: WeekDay,
    var openTime: LocalTime,
    var closeTime: LocalTime
)
