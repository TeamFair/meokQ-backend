package com.meokq.api.market.enums

import java.time.DayOfWeek
import java.time.LocalDateTime

enum class WeekDay(val dayOfWeek: DayOfWeek) {
    MON(DayOfWeek.MONDAY),
    TUE(DayOfWeek.TUESDAY),
    WED(DayOfWeek.WEDNESDAY),
    THU(DayOfWeek.THURSDAY),
    FRI(DayOfWeek.FRIDAY),
    SAT(DayOfWeek.SATURDAY),
    SUN(DayOfWeek.SUNDAY),
    ;

    companion object {
        fun getToday(): WeekDay {
            val today = LocalDateTime.now().dayOfWeek
            return values().first { it.dayOfWeek == today }
        }
    }
}