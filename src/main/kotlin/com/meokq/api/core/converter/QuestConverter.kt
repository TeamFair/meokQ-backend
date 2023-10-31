package com.meokq.api.core.converter

import com.meokq.api.application.model.Quest
import com.meokq.api.application.model.Reward
import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.request.RewardRequest
import com.meokq.api.application.response.QuestResponse
import com.meokq.api.application.response.RewardResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QuestConverter : BaseConverter<QuestRequest, QuestResponse, Quest> {

    @Autowired
    lateinit var rewardConverter : RewardConverter

    @Autowired
    lateinit var missionConverter: MissionConverter

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

    fun modelToResponse(models: List<Quest>): List<QuestResponse> {
        return models.map { modelToResponse(it) }
    }

    fun requestToModel(requests: List<QuestRequest>): List<Quest> {
        return requests.map { requestToModel(it) }
    }
}