package com.meokq.api.quest.service

import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestCreateResp
import com.meokq.api.quest.response.QuestDetailResp
import com.meokq.api.quest.response.QuestListResp
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestService(
    private val repository : QuestRepository,
    //private val missionService: MissionService,
    //private val rewardService: RewardService,

) : JpaService<Quest, String>, JpaSpecificationService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository
    override val jpaSpecRepository: BaseRepository<Quest, String> = repository
    private val specifications = QuestSpecification

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestListResp> {
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.map{
            //val mission = it.questId?.let { id -> missionService.findModelsByQuestId(id).firstOrNull() }
            //val reward = it.questId?.let { id -> rewardService.findModelsByQuestId(id).firstOrNull() }
            QuestListResp(it)
        }

        val count = repository.count(specification)
        return PageImpl(responses, pageable, count)
    }

    fun findById(questId : String) : QuestDetailResp {
        val quest = findModelById(questId)
        //missionService.findModelsByQuestId(questId).also { quest.missions = it }
        //rewardService.findModelsByQuestId(questId).also { quest.rewards = it }

        return QuestDetailResp(quest)
    }

    fun save(request: QuestCreateReq) : QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        val model = repository.save(modelForSave)

        model.questId.also {
            checkNotNullData(it, "해당 퀘스트에는 마켓정보가 등록되어 있지 않습니다.")

            // save mission
            //missionService.saveAll(it!!, request.missions)

            // save reward
            //rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    fun count(searchDto: QuestSearchDto): Long {
        return countBy(specifications.bySearchDto(searchDto))
    }
}