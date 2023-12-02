package com.meokq.api.user.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.user.request.BossReq
import com.meokq.api.user.model.Boss
import com.meokq.api.user.response.BossResp
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