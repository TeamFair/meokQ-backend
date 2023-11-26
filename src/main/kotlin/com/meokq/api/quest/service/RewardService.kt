package com.meokq.api.quest.service

import com.meokq.api.quest.response.RewardResp
import com.meokq.api.quest.converter.RewardConverter
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.request.RewardReq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RewardService {
    @Autowired
    lateinit var repository: RewardRepository

    @Autowired
    lateinit var converter: RewardConverter

    fun saveAll(questId: String, request: List<RewardReq>) : List<RewardResp>{
        val model = converter.requestToModel(request)
        model.forEach { it.questId = questId }
        val result = repository.saveAll(model)
        return converter.modelToResponse(result)
    }

    fun findAllByQuestId(questId: String) : List<RewardResp> {
        val result = repository.findAllByQuestId(questId)
        return converter.modelToResponse(result)
    }
}