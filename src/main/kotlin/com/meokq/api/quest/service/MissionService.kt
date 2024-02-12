package com.meokq.api.quest.service

import com.meokq.api.core.JpaService
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.repository.MissionRepository
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.response.MissionResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MissionService(
    val repository: MissionRepository,
): JpaService<Mission, String> {

    override var jpaRepository: JpaRepository<Mission, String> = repository

    fun saveAll(questId: String, requests: List<MissionReq>) : List<MissionResp>{
        val models = requests.map { Mission(it, questId) }
        val result = saveModels(models)
        return result.map { MissionResp(it) }
    }

    fun findModelsByQuestId(questId: String) : List<Mission> {
        return repository.findAllByQuestId(questId)
    }

    fun deleteAllByQuestId(questId: String) {
        return repository.deleteAllByQuestId(questId)
    }
}