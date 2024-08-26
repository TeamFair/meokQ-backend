package com.meokq.api.quest.service


import com.meokq.api.core.JpaService
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
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
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

    fun grantRewardsToUserForQuest(questId: String, targetMetadata: TargetMetadata) {
        val rewards = findModelsByQuestId(questId)
        rewards
            .filter { it.type == RewardType.XP }
            .forEach { gainXp(targetMetadata,it.quantity!!.toLong()) }

    }

    private fun gainXp(targetMetadata: TargetMetadata, xpPoint: Long) {
        customerRepository.findById(targetMetadata.userId).ifPresent{ it.gainXp(xpPoint) }
        xpHistoryService.save(
            UserAction.CHALLENGE_REGISTER.xpCustomer(xpPoint),
            TargetMetadata(
                targetType = targetMetadata.targetType,
                targetId = targetMetadata.targetId,
                userId = targetMetadata.userId
            )
        )
    }

    fun deleteAllByQuestId(questId: String) {
        repository.deleteAllByQuestId(questId)
    }

    fun returnXp(targetId: String, userAction: UserAction) {
        xpHistoryService.findAndWithdrawXp(targetId, userAction)?.let { xpHistory ->
            customerRepository.findById(xpHistory.userId).ifPresent { customer ->
                customer.gainXp(-xpHistory.xpPoint)
            }
        }
    }




}