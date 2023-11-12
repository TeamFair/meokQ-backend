package com.meokq.api.application.converter

import com.meokq.api.application.model.Mission
import com.meokq.api.application.request.MissionReq
import com.meokq.api.application.response.MissionResp
import org.springframework.stereotype.Component

@Component
class MissionConverter : BaseConverter<MissionReq, MissionResp, Mission> {
    override fun modelToResponse(model: Mission): MissionResp {
        return MissionResp(
            content = model.content,
            target = model.target,
            quantity = model.quantity,
            type = model.type,
        )
    }

    override fun requestToModel(request: MissionReq): Mission {
        return Mission(
            content = request.content,
            target = request.target,
            quantity = request.quantity,
            type = request.type
        )
    }

}