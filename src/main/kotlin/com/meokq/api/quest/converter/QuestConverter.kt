package com.meokq.api.quest.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.response.QuestResp
import org.springframework.stereotype.Component

@Component
class QuestConverter : BaseConverter<QuestReq, QuestResp, Quest> {

    override fun modelToResponse(model: Quest): QuestResp {
        return QuestResp(
            questId = model.questId,
            marketId = model.marketId,
        )
    }

    override fun requestToModel(request: QuestReq): Quest {
        return Quest(
            marketId = request.marketId,
        )
    }
}