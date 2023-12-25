package com.meokq.api.quest.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import org.springframework.stereotype.Component

@Component
@Deprecated("converter not used")
class RewardConverter : BaseConverter<RewardReq, RewardResp, Reward> {
    override fun modelToResponse(model: Reward) = RewardResp(model)
    override fun requestToModel(request: RewardReq) = Reward(request)
}