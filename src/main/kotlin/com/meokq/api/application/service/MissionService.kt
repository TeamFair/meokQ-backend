package com.meokq.api.application.service

import com.meokq.api.application.converter.MissionConverter
import com.meokq.api.application.repository.MissionRepository
import com.meokq.api.application.request.MissionReq
import com.meokq.api.application.response.MissionResp
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