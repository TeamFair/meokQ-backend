package com.meokq.api.quest.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.response.MissionResp
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