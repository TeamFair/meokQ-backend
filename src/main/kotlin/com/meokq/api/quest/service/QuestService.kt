package com.meokq.api.quest.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.file.request.ImageReq
import com.meokq.api.file.service.ImageService
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestHistoryRepository
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestCreateResp
import com.meokq.api.quest.response.QuestDeleteResp
import com.meokq.api.quest.response.QuestDetailResp
import com.meokq.api.quest.response.QuestListResp
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class QuestService(
    private val repository : QuestRepository,
    private val missionService: MissionService,
    private val rewardService: RewardService,
    private val questHistoryRepository: QuestHistoryRepository,
    private val imageService : ImageService,

) : JpaService<Quest, String>, JpaSpecificationService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository
    override val jpaSpecRepository: BaseRepository<Quest, String> = repository
    private val specifications = QuestSpecification

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestListResp> {
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.map {
            it.questId?.let { id -> it.missions = missionService.findModelsByQuestId(id)
                                    it.rewards = rewardService.findModelsByQuestId(id)
            }
            QuestListResp(it)
        }

        return PageImpl(responses, pageable, models.size.toLong())
    }

    fun findById(questId : String) : QuestDetailResp {
        val quest = findModelById(questId)
        missionService.findModelsByQuestId(questId).also { quest.missions = it }
        rewardService.findModelsByQuestId(questId).also { quest.rewards = it }

        return QuestDetailResp(quest)
    }

    fun save(request: QuestCreateReq) : QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        val model = saveModel(modelForSave)


        model.questId.also {
            checkNotNullData(it, "해당 퀘스트에는 마켓정보가 등록되어 있지 않습니다.")

            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    fun adminSave(request: QuestCreateReqForAdmin, imageRequest : ImageReq, authReq: AuthReq) : QuestCreateResp {
        val imageId = imageService.save(imageRequest,authReq)

        // save quest
        val modelForSave = Quest(request)
        modelForSave.status = QuestStatus.PUBLISHED
        modelForSave.addImageId(imageId.imageId!!)

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

    fun count(searchDto: QuestSearchDto): Long {
        return countBy(specifications.bySearchDto(searchDto))
    }

    fun getCompletedQuests(pageable: Pageable ,authReq: AuthReq): PageImpl<QuestListResp> {
        val questHistories = questHistoryRepository.findByCustomerId(authReq.userId!!,pageable)
        val questIds = questHistories.content.map { it.questId!! }
        val models = questIds.map{ findModelById(it) }
        val responses = models.map { QuestListResp(it) }

        return PageImpl(responses, pageable, questHistories.totalElements)
    }

    fun getUncompletedQuests(pageable: Pageable, authReq: AuthReq): PageImpl<QuestListResp> {
        val questHistories = questHistoryRepository.findByCustomerId(authReq.userId!!,pageable)
        val questIds = questHistories.content.map { it.questId!! }
        val models = repository.findAllByQuestIdNotInAndStatus(questIds,QuestStatus.PUBLISHED)
        val responses = models.map { QuestListResp(it) }

        return PageImpl(responses, pageable, questHistories.totalElements)
    }

    fun delete(questId: String): QuestDeleteResp {
        val quest = findModelById(questId)
        missionService.deleteAllByQuestId(questId)
        rewardService.deleteAllByQuestId(questId)
        repository.delete(quest)
        return QuestDeleteResp(questId)
    }

}