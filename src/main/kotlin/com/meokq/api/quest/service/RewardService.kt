package com.meokq.api.quest.service


import com.meokq.api.core.JpaService
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHistoryService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class RewardService(
    private val repository: RewardRepository,
    private val customerRepository: CustomerRepository,
    private val xpHistoryService: XpHistoryService,

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
        returnXp(questId)
        return repository.deleteAllByQuestId(questId)
    }

    private fun returnXp(questId: String) {
        val xpHistoryInfoList = xpHistoryService.deleteByTargetId(questId)
        xpHistoryInfoList.forEach {
            customerRepository.findById(it.userId).ifPresent {
                it.gainXp(-it.xpPoint!!)
            }
        }
    }

    fun gainRewardByQuestId(questId: String, userId: String) {
        val rewards = findModelsByQuestId(questId)
        gainXp(rewards, userId)
    }

    private fun gainXp(rewards: List<Reward>, userId: String) {
        rewards
            .filter { it.type == RewardType.XP }
            .map {
                val customer = customerRepository.findById(userId).orElseThrow { NotFoundException("customer not found") }
                customer.gainXp(it.quantity?.toLong()?:0L)

                xpHistoryService.save(
                    UserAction.CHALLENGE_REGISTER.xpCustomer(it.quantity?.toLong()?:0L),
                    TargetMetadata(
                        targetType = TargetType.QUEST,
                        targetId = it.questId!!,
                        userId = userId
                    )
                )
            }
    }


}