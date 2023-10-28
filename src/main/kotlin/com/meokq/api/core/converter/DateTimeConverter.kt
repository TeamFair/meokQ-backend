package com.meokq.api.core.converter

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class DateTimeConverter {
    fun convertToString(dateTime: LocalDateTime?): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return dateTime?.format(formatter)
    }
}