package com.meokq.api.quest.response

import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Reward
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 따로 연결된 정보가 없어서, 용도별로 response 를 나누지는 않았음.
 */
@Schema(description = "Reward-Response")
class RewardResp(
    val rewardId : String?,
    val content: String?,
    val target: String?,
    val quantity: Int?,
    val discountRate : Int?,
    val type : RewardType?,

    val title : String? = null,
){
    constructor(model : Reward) : this(
        rewardId = model.rewardId,
        content = model.content,
        target = model.target,
        quantity = model.quantity,
        discountRate = model.discountRate,
        type = model.type,
        title = model.type?.getTitle(model)
    )
}
