package com.meokq.api.quest.request

import com.meokq.api.quest.enums.RewardType
import io.swagger.v3.oas.annotations.media.Schema

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
)