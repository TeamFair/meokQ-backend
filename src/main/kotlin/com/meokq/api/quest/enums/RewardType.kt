package com.meokq.api.quest.enums

import com.meokq.api.quest.model.Reward

enum class RewardType {
    GIFT, // 증정
    DISCOUNT, // 할인
    XP, // 경험치 부여
    ;

    companion object {
        fun getTitle(reward: Reward) : String{
            return when(reward.type){
                GIFT -> return "${reward.target} ${reward.quantity}개(잔) 증정권"
                DISCOUNT -> return "${reward.target} ${reward.discountRate}% 할인권"
                XP -> return "${reward.quantity}xp 경험치 부여"
                else -> {throw Exception("지원하지 않는 type 입니다.") }
            }
        }
    }
}