package com.meokq.api.quest.service


import com.meokq.api.core.JpaService
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class RewardService(
    private val repository: RewardRepository,
) : JpaService<Reward, String> {
    override var jpaRepository: JpaRepository<Reward, String> = repository

    fun saveAll(questId: String, requests: List<RewardReq>) : List<RewardResp>{
        requests.forEach { it.validate() }

        val models = requests.map { Reward(it, questId) }
        val result = saveModels(models)
        return result.map { RewardResp(it) }
    }

    fun findModelsByQuestId(questId: String) : List<Reward> {
        return repository.findAllByQuestId(questId)
    }

    fun deleteAllByQuestId(questId: String) {
        return repository.deleteAllByQuestId(questId)
    }
}