package com.meokq.api.core.enums

import com.meokq.api.core.exception.NotFoundException

enum class TargetType {
    QUEST,CHALLENGE,EMOJI;

    companion object {
        fun fromString(value: String): TargetType {
            return when (value.uppercase()) {
                "QUEST" -> QUEST
                "CHALLENGE" -> CHALLENGE
                "EMOJI" -> EMOJI
                else -> throw NotFoundException("존재하지 않는 TargetType 입니다.")
            }
        }
    }
}
