package com.meokq.api.quest.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.response.MissionResp
import org.springframework.stereotype.Component

@Component
@Deprecated("converter to model")
class MissionConverter : BaseConverter<MissionReq, MissionResp, Mission> {
    override fun modelToResponse(model: Mission) = MissionResp(model)

    override fun requestToModel(request: MissionReq) = Mission(request)

}