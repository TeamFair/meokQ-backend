package com.meokq.api.quest.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestHistoryRepository
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.repository.queryDSL.QuestCustomRepositoryImpl
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.request.QuestUpdateReq
import com.meokq.api.quest.response.*
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class QuestService(
    private val repository: QuestRepository,
    private val missionService: MissionService,
    private val rewardService: RewardService,
    private val questHistoryRepository: QuestHistoryRepository,
    private val challengeService: ChallengeService,
    private val questCustomRepositoryImpl: QuestCustomRepositoryImpl

    ) : JpaService<Quest, String>, JpaSpecificationService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository
    override val jpaSpecRepository: BaseRepository<Quest, String> = repository
    private val specifications = QuestSpecification


    @Transactional(readOnly = true)
    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestQueryDSLListResp> {
        val models = questCustomRepositoryImpl.findAll(searchDto,pageable)
        return PageImpl(models.content, pageable, models.totalElements)
    }

    fun findById(questId: String): QuestDetailResp {
        val quest = findModelById(questId)
        missionService.findModelsByQuestId(questId).also { quest.missions = it }
        rewardService.findModelsByQuestId(questId).also { quest.rewards = it }
        return QuestDetailResp(quest)
    }

    fun save(request: QuestCreateReq): QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        val model = saveModel(modelForSave)
        model.questId.also {
            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    fun adminSave(request: QuestCreateReqForAdmin): QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        modelForSave.addImageId(request.imageId)

        val model = repository.save(modelForSave)

        model.questId.also {
            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    fun update(id: String, request: QuestUpdateReq): QuestCreateResp {
        val model = findModelById(id)
        missionService.deleteAllByQuestId(model.questId!!)
        rewardService.deleteAllByQuestId(model.questId!!)

        model.questId.also {
            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        model.refreshFields(request)
        saveModel(model)

        return QuestCreateResp(model)
    }

    fun count(searchDto: QuestSearchDto): Long {
        return countBy(specifications.bySearchDto(searchDto))
    }

    fun getCompletedQuests(pageable: Pageable, authReq: AuthReq): Page<QuestQueryDSLListResp> {
//        val specification = specifications.completedQuestList(authReq.userId!!)
//        val models = findAllBy(specification, pageable)
        return questCustomRepositoryImpl.getCompletedQuests(pageable,authReq.userId!!)

    }

    fun getUncompletedQuests(pageable: Pageable, authReq: AuthReq): Page<QuestQueryDSLListResp> {
//        val specification = specifications.uncompletedQuestList(authReq.userId!!)
//        val models = findAllBy(specification, pageable)
        return questCustomRepositoryImpl.getUnCompletedQuests(pageable,authReq.userId!!)
    }

    @Transactional
    fun softDelete(questId: String): QuestDeleteResp {
        val quest = findModelById(questId)
        quest.softDelete()
        saveModel(quest)
        return QuestDeleteResp(questId)
    }

    @Transactional
    fun hardDelete(questId: String, authReq: AuthReq): QuestDeleteResp {
        challengeService.deleteAllByQuestId(questId, authReq)
        questHistoryRepository.deleteAllByQuestId(questId)
        missionService.deleteAllByQuestId(questId)
        rewardService.deleteAllByQuestId(questId)
        deleteById(questId)
        return QuestDeleteResp(questId)
    }




}