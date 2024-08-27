package com.meokq.api.quest.service


import com.meokq.api.core.JpaService
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.response.RewardResp
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.model.XpType
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RewardService(
    private val repository: RewardRepository,
    private val customerRepository: CustomerRepository,
    private val xpService: XpService,


    ) : JpaService<Reward, String> {
    override var jpaRepository: JpaRepository<Reward, String> = repository

    fun saveAll(questId: String, requests: List<RewardReq>) : List<RewardResp>{
        requests.forEach {
            if (it.type == RewardType.XP) {
                contentValidator(it)
            }
        }
        val models = requests.map { Reward(it, questId) }
        val result = saveModels(models)
        return result.map { RewardResp(it) }
    }

    private fun contentValidator(request: RewardReq) {
        val content = request.content ?: throw IllegalArgumentException("Content 는 필수 값 입니다.")
        if (!XpType.validate(content)) {
            throw IllegalArgumentException("선택할 수 없는 Xp 타입 입니다: $content")
        }
    }

    fun grantRewardsToUserForQuest(questId: String, targetMetadata: TargetMetadata) {
        val rewards = findModelsByQuestId(questId)
        rewards
            .filter { it.type == RewardType.XP }
            .forEach { gainXp(targetMetadata,it) }
    }

    private fun gainXp(metadata: TargetMetadata, reward: Reward) {
        val xpType = XpType.valueOf(reward.content?: throw IllegalArgumentException("XpType 이 올바르지 않습니다."))
        val userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(xpType, reward.quantity!!.toLong())
        xpService.register(userAction,metadata)
    }


    fun deleteAllByQuestId(questId: String) {
        repository.deleteAllByQuestId(questId)
    }

    fun returnXp(metadata: TargetMetadata, userAction: UserAction) {
        xpService.delete(userAction,metadata)
    }


    fun findModelsByQuestId(questId: String) : List<Reward> {
        return repository.findAllByQuestId(questId)
    }






}