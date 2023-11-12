package com.meokq.api.application.converter

import com.meokq.api.application.model.Reward
import com.meokq.api.application.request.RewardReq
import com.meokq.api.application.response.RewardResp
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