package com.meokq.api.core.converter

import com.meokq.api.core.enums.DateTimePattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateTimeConverterV2 {
    fun convertToString(dateTime: LocalDateTime, pattern : DateTimePattern = DateTimePattern.STANDARD): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern.pattern)
        return dateTime.format(formatter)
    }

    fun convertToDateTime(dateTimeStr: String, pattern: DateTimePattern = DateTimePattern.STANDARD) : LocalDateTime {
        try {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern.pattern)
            return LocalDateTime.parse(dateTimeStr, formatter)
        } catch (e: DateTimeParseException) {
            throw Exception("DateTimeParseException : Invalid format")
        }
    }
}