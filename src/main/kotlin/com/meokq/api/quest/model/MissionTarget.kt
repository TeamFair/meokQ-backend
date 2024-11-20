package com.meokq.api.quest.model

import jakarta.validation.ValidationException

enum class MissionTarget {
    DAILY,
    WEEKLY,
    MONTHLY,

    // 유효하지 않거나, 비어있지 않은 데이터
    NONE,

    // TODO delete
    XP,
    ;

    companion object {
        fun valueOfWithThrow(value: String?): MissionTarget {
            try {
                return MissionTarget.valueOf(value!!)
            } catch (e: Exception) {
                throw ValidationException("미션 유형에 ${value}은/는 유효하지 않습니다.")
            }
        }
    }
}
