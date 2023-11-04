package com.meokq.api.core.converter

import com.meokq.api.application.model.Mission
import com.meokq.api.application.request.MissionRequest
import com.meokq.api.application.response.MissionResponse
import org.springframework.stereotype.Component

@Component
class MissionConverter : BaseConverter<MissionRequest, MissionResponse, Mission> {
    override fun modelToResponse(model: Mission): MissionResponse {
        return MissionResponse(
            content = model.content,
            target = model.target,
            quantity = model.quantity
        )
    }

    override fun requestToModel(request: MissionRequest): Mission {
        return Mission(
            content = request.content,
            target = request.target,
            quantity = request.quantity,
        )
    }

}