package com.meokq.api.emoji.enums

import com.meokq.api.core.exception.NotFoundException

enum class TargetType {
    QUEST,CHALLENGE;
    companion object {
        fun fromString(value: String): TargetType? {
            return when (value.uppercase()) {
                "QUEST" -> QUEST
                "CHALLENGE" -> CHALLENGE
                else -> throw NotFoundException("존재하지 않는 TargetType 입니다.")
            }
        }
    }
}
