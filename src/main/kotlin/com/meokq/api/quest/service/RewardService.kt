package com.meokq.api.quest.service


import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.converter.RewardConverter
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class RewardService(
    private val repository: RewardRepository,
    private val converter: RewardConverter
) : BaseService<RewardReq, RewardResp, Reward, String> {
    override var _converter: BaseConverter<RewardReq, RewardResp, Reward> = converter
    override var _repository: JpaRepository<Reward, String> = repository

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

    fun deleteAllByQuestId(questId: String) {
        return repository.deleteAllByQuestId(questId)
    }
}