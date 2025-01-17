package com.meokq.api.quest.enums

import com.meokq.api.quest.model.Mission
import jakarta.validation.ValidationException

enum class QuestType {
    NORMAL,
    REPEAT
    ;

    companion object {
        fun valueOfWithThrow(value: String?): QuestType {
            try {
                return QuestType.valueOf(value!!)
            } catch (e: Exception) {
                throw ValidationException("퀘스트 유형에 ${value}은/는 유효하지 않습니다.")
            }
        }
    }

}
