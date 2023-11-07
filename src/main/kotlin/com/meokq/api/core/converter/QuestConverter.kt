package com.meokq.api.core.converter

import com.meokq.api.application.model.Quest
import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.response.QuestResponse
import org.springframework.stereotype.Component

@Component
class QuestConverter : BaseConverter<QuestRequest, QuestResponse, Quest> {

    override fun modelToResponse(model: Quest): QuestResponse {
        return QuestResponse(
            questId = model.questId,
        )
    }

    override fun requestToModel(request: QuestRequest): Quest {
        return Quest(
            marketId = request.marketId,
        )
    }
}