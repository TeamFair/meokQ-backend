package com.meokq.api.application.service

import com.meokq.api.application.model.Quest
import com.meokq.api.application.repository.QuestRepository
import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.response.QuestResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.QuestConverter
import com.meokq.api.core.exception.NotFoundException
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
) : BaseService<QuestRequest, QuestResponse, Quest, String> {
    override var _converter: BaseConverter<QuestRequest, QuestResponse, Quest> = converter
    override var _repository: JpaRepository<Quest, String> = repository

    fun findAllByMarketId(marketId : String, pageable: Pageable) : Page<QuestResponse>{
        val page = repository.findAllByMarketId(marketId, pageable)
        val content = converter.modelToResponse(page.content)
        content.forEach {
            if (it.questId == null) throw Exception("Failed to save the quest.")
            it.missions = missionService.findAllByQuestId(it.questId)
            it.rewards = rewardService.findAllByQuestId(it.questId)
        }
        return PageImpl(content, pageable, page.totalElements)
    }

    fun findById(questId : String) : QuestResponse {
        val quest = repository.findById(questId).orElseThrow { throw NotFoundException("quest is not found!!") }
        val questResp = converter.modelToResponse(quest)
        missionService.findAllByQuestId(questId).also { questResp.missions = it }
        rewardService.findAllByQuestId(questId).also { questResp.rewards = it }
        return questResp
    }

    override fun save(request : QuestRequest) : QuestResponse {
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