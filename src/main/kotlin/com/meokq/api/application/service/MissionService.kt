package com.meokq.api.application.service

import com.meokq.api.application.repository.MissionRepository
import com.meokq.api.application.request.MissionRequest
import com.meokq.api.application.response.MissionResponse
import com.meokq.api.core.converter.MissionConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MissionService {
    @Autowired
    lateinit var repository: MissionRepository

    @Autowired
    lateinit var converter: MissionConverter

    fun saveAll(questId: String, requests: List<MissionRequest>) : List<MissionResponse>{
        val model = converter.requestToModel(requests)
        model.forEach { it.questId = questId }
        val result = repository.saveAll(model)
        return converter.modelToResponse(result)
    }

    fun findAllByQuestId(questId: String) : List<MissionResponse> {
        val result = repository.findAllByQuestId(questId)
        return converter.modelToResponse(result)
    }
}