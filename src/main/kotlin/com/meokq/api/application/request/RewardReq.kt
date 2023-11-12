package com.meokq.api.application.request

import com.meokq.api.application.enums.RewardType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Reward-Request")
class RewardReq (
    @Schema(description = "보상 설명(자유형식)")
    val content : String?,

    @Schema(description = "보상 물품")
    val target : String?,

    @Schema(description = "보상 개수")
    val quantity : Int?,

    @Schema(description = "보상 할인율")
    val discountRate : Int?,

    @Schema(description = "보상 종류")
    val type : RewardType
)