package com.meokq.api.quest.enums

import com.meokq.api.quest.model.Mission

enum class MissionType {
    FREE, // 자유 미션
    NORMAL // 일반 미션
    ;

    fun getTitle(mission: Mission) : String{
        return when(this){
            FREE -> return "${mission.content}"
            NORMAL -> return "${mission.target} ${mission.quantity}개(잔) 주문"
        }
    }
}