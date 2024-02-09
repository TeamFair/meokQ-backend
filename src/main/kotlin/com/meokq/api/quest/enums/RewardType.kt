package com.meokq.api.quest.enums

import com.meokq.api.quest.model.Reward

enum class RewardType {
    GIFT, // 증정
    DISCOUNT // 할인
    ;

    fun getTitle(reward: Reward) : String{
        return when(this){
            GIFT -> return "${reward.target} ${reward.quantity}개(잔) 증정권"
            DISCOUNT -> return "${reward.target} ${reward.discountRate}% 할인권"
        }
    }
}