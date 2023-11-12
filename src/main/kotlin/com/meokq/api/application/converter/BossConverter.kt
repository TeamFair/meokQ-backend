package com.meokq.api.application.converter

import com.meokq.api.application.model.Boss
import com.meokq.api.application.request.BossReq
import com.meokq.api.application.response.BossResp
import org.springframework.stereotype.Component

@Component
class BossConverter : BaseConverter<BossReq, BossResp, Boss> {
    override fun modelToResponse(model: Boss): BossResp {
        return BossResp(
            status = model.status,
            bossId = model.bossId
        )
    }

    override fun requestToModel(request: BossReq): Boss {
        return Boss(
            email = request.email,
        )
    }
}