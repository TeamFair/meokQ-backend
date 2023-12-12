package com.meokq.api.quest.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.converter.QuestConverter
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestService(
    private val repository : QuestRepository,
    private val converter : QuestConverter,
    private val missionService: MissionService,
    private val rewardService: RewardService
) : BaseService<QuestReq, QuestResp, Quest, String> {
    override var _converter: BaseConverter<QuestReq, QuestResp, Quest> = converter
    override var _repository: JpaRepository<Quest, String> = repository

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestResp> {
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        val specification = QuestSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)
        val content = page.content.map{ converter.modelToResponse(it) }

        /**
         * TODO : quest-list를 조회할 때에도 미션과 보상정보를 모두 보여주는 것은 비효율적이라고 생각.
          */
        content.forEach {
            if (it.questId == null) throw Exception("Failed to save the quest.")
            it.missions = missionService.findAllByQuestId(it.questId)
            it.rewards = rewardService.findAllByQuestId(it.questId)
        }

        return PageImpl(content, pageable, page.numberOfElements.toLong())
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