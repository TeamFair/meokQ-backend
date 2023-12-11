package com.meokq.api.quest.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.converter.QuestConverter
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.response.QuestResp
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestService(
    final val repository : QuestRepository,
    final val converter : QuestConverter,
    final val missionService: MissionService,
    final val rewardService: RewardService
) : BaseService<QuestReq, QuestResp, Quest, String> {
    override var _converter: BaseConverter<QuestReq, QuestResp, Quest> = converter
    override var _repository: JpaRepository<Quest, String> = repository

    fun findAllByMarketId(marketId : String, pageable: Pageable) : Page<QuestResp>{
        val page = repository.findAllByMarketId(marketId, pageable)
        val content = converter.modelToResponse(page.content)
        content.forEach {
            if (it.questId == null) throw Exception("Failed to save the quest.")
            it.missions = missionService.findAllByQuestId(it.questId)
            it.rewards = rewardService.findAllByQuestId(it.questId)
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    override fun findById(questId : String) : QuestResp {
        val quest = repository.findById(questId).orElseThrow { throw NotFoundException("quest is not found!!") }
        val questResp = converter.modelToResponse(quest)
        missionService.findAllByQuestId(questId).also { questResp.missions = it }
        rewardService.findAllByQuestId(questId).also { questResp.rewards = it }
        return questResp
    }

    override fun save(request : QuestReq) : QuestResp {
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

    override fun deleteById(questId: String) {
        // delete quest
        super.deleteById(questId)

        // delete mission
        missionService.deleteAllByQuestId(questId)

        // delete reward
        rewardService.deleteAllByQuestId(questId)
    }
}