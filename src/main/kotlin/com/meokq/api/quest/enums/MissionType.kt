package com.meokq.api.quest.enums

import com.meokq.api.quest.model.Mission

enum class MissionType {
    FREE, // 자유 미션
    NORMAL, // 일반 미션

    // 2025-02-16
    OX, //OX
    WORDS // 단답형
    ;

    companion object{
        fun getTitle(mission: Mission) : String{
            if (mission.type == null) return ""

            return when(mission.type){
                NORMAL -> return "${mission.target} ${mission.quantity}개(잔) 주문"
                else -> return "${mission.content}"
            }
        }
    }
}
