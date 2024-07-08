package com.meokq.api.emoji.enums

import com.meokq.api.core.exception.NotFoundException

enum class EmojiStatus {
    LIKE,HATE;
    companion object {
        fun fromString(value: String): EmojiStatus {
            return when (value.uppercase()) {
                "LIKE" -> LIKE
                "HATE" -> HATE
                else -> throw NotFoundException("존재하지 않는 EmojiStatus 입니다.")
            }
        }
    }


}
