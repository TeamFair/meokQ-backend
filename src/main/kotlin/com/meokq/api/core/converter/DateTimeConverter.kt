package com.meokq.api.core.converter

import com.meokq.api.core.enums.DateTimePattern
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class DateTimeConverter {
    fun convertToString(dateTime: LocalDateTime, pattern : DateTimePattern = DateTimePattern.STANDARD): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern.pattern)
        return dateTime.format(formatter)
    }
}