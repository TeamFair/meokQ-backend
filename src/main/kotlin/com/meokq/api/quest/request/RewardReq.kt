package com.meokq.api.quest.request

import com.meokq.api.quest.enums.RewardType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.ValidationException

@Schema(name = "Reward-Request")
class RewardReq (
    @Schema(description = "보상 설명(자유형식)", example = "")
    val content : String?,

    @Schema(description = "보상 물품", example = "쿠키")
    val target : String?,

    @Schema(description = "보상 개수", example = "1")
    val quantity : Int?,

    @Schema(description = "보상 할인율", example = "0")
    val discountRate : Int?,

    @Schema(description = "보상 종류", type = "GIFT")
    val type : RewardType
){
    fun validate(){
        when (type){
            RewardType.GIFT -> {
                if (target.isNullOrBlank()) throw ValidationException("보상물품은 비어있을 수 없습니다.")
                if (quantity == null || !(1..100).contains(quantity)) throw ValidationException("보상개수는 1~100 사이의 수여야 합니다.")
            }
            RewardType.DISCOUNT -> {
                if (target.isNullOrBlank()) throw ValidationException("보상물품은 비어있을 수 없습니다.")
                if (discountRate == null || !(1..99).contains(discountRate)) throw ValidationException("할인율은 1~99 사이의 수여야 합니다.")
            }
            RewardType.XP -> {
                if (quantity == null || !(1..1000).contains(quantity)) throw ValidationException("경험치는 1~1000 사이의 수여야 합니다.")
            }
            else -> {
                //
            }
        }
    }
}