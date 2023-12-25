package com.meokq.api.quest.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.converter.QuestConverter
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestCreateResp
import com.meokq.api.quest.response.QuestDetailResp
import com.meokq.api.quest.response.QuestListResp
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestService(
    private val repository : QuestRepository,
    private val converter : QuestConverter,
    private val missionService: MissionService,
    private val rewardService: RewardService,
) : BaseService<QuestReq, QuestResp, Quest, String> {
    override var _converter: BaseConverter<QuestReq, QuestResp, Quest> = converter
    override var _repository: JpaRepository<Quest, String> = repository

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestListResp> {
        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val specification = QuestSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)
        val content = page.content.map{
            val mission = it.questId?.let { id -> missionService.findModelsQuestId(id).firstOrNull() }
            val reward = it.questId?.let { id -> rewardService.findModelsByQuestId(id).firstOrNull() }
            QuestListResp(it, mission, reward)
        }

        val count = count(searchDto)
        return PageImpl(content, pageable, count)
    }

    fun findQuestById(questId : String) : QuestDetailResp {
        val quest = findModelById(questId)
        missionService.findModelsQuestId(questId).also { quest.missions = it }
        rewardService.findModelsByQuestId(questId).also { quest.rewards = it }

        return QuestDetailResp(quest)
    }

    fun saveQuest(request: QuestCreateReq) : QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        val model = repository.save(modelForSave)

        model.questId.also {
            checkNotNullData(it, "해당 퀘스트에는 마켓정보가 등록되어 있지 않습니다.")

            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    fun count(searchDto: QuestSearchDto) : Long {
        val specification = QuestSpecification.bySearchDto(searchDto)
        return repository.count(specification)
    }
}