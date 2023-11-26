package com.meokq.api.quest.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import org.springframework.stereotype.Component

@Component
class RewardConverter : BaseConverter<RewardReq, RewardResp, Reward> {
    override fun modelToResponse(model: Reward): RewardResp {
        return RewardResp(
            content = model.content,
            target = model.target,
            quantity = model.quantity,
            discountRate = model.discountRate,
            type = model.type
        )
    }

    override fun requestToModel(request: RewardReq): Reward {
        return Reward(
            content = request.content,
            target = request.target,
            quantity = request.quantity,
            discountRate = request.discountRate,
            type = request.type
        )
    }
}