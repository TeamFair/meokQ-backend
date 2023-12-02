package com.meokq.api.quest.service

import com.meokq.api.quest.converter.MissionConverter
import com.meokq.api.quest.repository.MissionRepository
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.response.MissionResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MissionService {
    @Autowired
    lateinit var repository: MissionRepository

    @Autowired
    lateinit var converter: MissionConverter

    fun saveAll(questId: String, requests: List<MissionReq>) : List<MissionResp>{
        val model = converter.requestToModel(requests)
        model.forEach { it.questId = questId }
        val result = repository.saveAll(model)
        return converter.modelToResponse(result)
    }

    fun findAllByQuestId(questId: String) : List<MissionResp> {
        val result = repository.findAllByQuestId(questId)
        return converter.modelToResponse(result)
    }
}