package com.meokq.api.application.service

import com.meokq.api.application.repository.QuestRepository
import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.response.QuestResponse
import com.meokq.api.core.converter.QuestConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestService {

    @Autowired
    lateinit var repository : QuestRepository

    @Autowired
    lateinit var converter : QuestConverter

    @Autowired
    lateinit var missionService: MissionService

    @Autowired
    lateinit var rewardService: RewardService

    fun save(request : QuestRequest) : QuestResponse {
        // save quest
        val notice = converter.requestToModel(request)
        val model = repository.save(notice)
        if (model.questId == null) throw Exception("Failed to save the quest.")
        val response = converter.modelToResponse(model)

        // save mission
        response.missions = missionService.saveAll(model.questId!!, request.missions)

        // save reward
        response.rewards = rewardService.saveAll(model.questId!!, request.rewards)

        return response
    }

}