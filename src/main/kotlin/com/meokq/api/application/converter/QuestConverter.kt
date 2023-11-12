package com.meokq.api.application.converter

import com.meokq.api.application.model.Quest
import com.meokq.api.application.request.QuestReq
import com.meokq.api.application.response.QuestResp
import org.springframework.stereotype.Component

@Component
class QuestConverter : BaseConverter<QuestReq, QuestResp, Quest> {

    override fun modelToResponse(model: Quest): QuestResp {
        return QuestResp(
            questId = model.questId,
        )
    }

    override fun requestToModel(request: QuestReq): Quest {
        return Quest(
            marketId = request.marketId,
        )
    }
}