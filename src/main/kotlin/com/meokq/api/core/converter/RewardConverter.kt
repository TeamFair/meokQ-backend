package com.meokq.api.core.converter

import com.meokq.api.application.model.Reward
import com.meokq.api.application.request.RewardRequest
import com.meokq.api.application.response.RewardResponse
import org.springframework.stereotype.Component

@Component
class RewardConverter : BaseConverter<RewardRequest, RewardResponse, Reward> {
    override fun modelToResponse(model: Reward): RewardResponse {
        return RewardResponse(
            content = model.content,
            target = model.target,
            quantity = model.quantity
        )
    }

    override fun requestToModel(request: RewardRequest): Reward {
        return Reward(
            content = request.content,
            target = request.target,
            quantity = request.quantity
        )
    }

    fun modelToResponse(models: List<Reward>): List<RewardResponse> {
        return models.map { modelToResponse(it) }
    }

    fun requestToModel(requests: List<RewardRequest>): List<Reward> {
        return requests.map { requestToModel(it) }
    }
}