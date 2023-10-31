package com.meokq.api.application.service

import com.meokq.api.application.repository.RewardRepository
import com.meokq.api.application.request.RewardRequest
import com.meokq.api.application.response.RewardResponse
import com.meokq.api.core.converter.RewardConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RewardService {
    @Autowired
    lateinit var repository: RewardRepository

    @Autowired
    lateinit var converter: RewardConverter

    fun saveAll(questId: String, request: List<RewardRequest>) : List<RewardResponse>{
        val model = converter.requestToModel(request)
        model.forEach { it.questId = questId }
        val result = repository.saveAll(model)
        return converter.modelToResponse(result)
    }
}