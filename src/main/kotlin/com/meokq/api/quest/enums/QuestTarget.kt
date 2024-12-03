package com.meokq.api.quest.enums

import jakarta.validation.ValidationException

enum class QuestTarget {
    DAILY,
    WEEKLY,
    MONTHLY,
    NONE,
    ;

    companion object {
        fun valueOfWithThrow(value: String?): QuestTarget {
            try {
                return QuestTarget.valueOf(value!!)
            } catch (e: Exception) {
                throw ValidationException("미션 유형에 ${value}은/는 유효하지 않습니다.")
            }
        }
    }
}
