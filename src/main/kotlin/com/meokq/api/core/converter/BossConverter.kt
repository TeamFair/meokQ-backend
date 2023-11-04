package com.meokq.api.core.converter

import com.meokq.api.application.model.Boss
import com.meokq.api.application.request.BossRequest
import com.meokq.api.application.response.BossResponse
import org.springframework.stereotype.Component

@Component
class BossConverter : BaseConverter<BossRequest, BossResponse, Boss> {
    override fun modelToResponse(model: Boss): BossResponse {
        return BossResponse(
            bossId = model.bossId,
        )
    }

    override fun requestToModel(request: BossRequest): Boss {
        return Boss(
            email = request.email,
            nickName = request.nickName,
        )
    }
}