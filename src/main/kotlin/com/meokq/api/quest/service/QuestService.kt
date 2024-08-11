package com.meokq.api.quest.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
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
import com.meokq.api.rank.ChallengeEmojiRankService
import com.meokq.api.xp.service.XpHistoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(
    private val repository : QuestRepository,
    private val missionService: MissionService,
    private val rewardService: RewardService,
    private val questHistoryRepository: QuestHistoryRepository,
    private val imageService: ImageService,
    private val challengeService: ChallengeService,

    ) : JpaService<Quest, String>, JpaSpecificationService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository
    override val jpaSpecRepository: BaseRepository<Quest, String> = repository
    private val specifications = QuestSpecification
    private companion object{
        val EMPTYUSERLIST = listOf("empty")
    }

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestListResp> {
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.content.map {
            it.questId?.let { id -> it.missions = missionService.findModelsByQuestId(id)
                                    it.rewards = rewardService.findModelsByQuestId(id)
            }
            QuestListResp(it)
        }

        return PageImpl(responses, pageable, models.totalElements)
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
            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }
        return QuestCreateResp(model)
    }

    @Transactional
    fun adminSave(request: QuestCreateReqForAdmin) : QuestCreateResp {
        // save quest
        val modelForSave = Quest(request)
        modelForSave.status = QuestStatus.PUBLISHED
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

    fun getUncompletedQuests(pageable: Pageable, authReq: AuthReq): Page<QuestListResp> {
        val questHistories = questHistoryRepository.findByCustomerId(authReq.userId!!, pageable)
        val questIds = questHistories.content.map { it.questId!! }
        val models = repository.findAllByQuestIdNotInAndStatus(questIds.ifEmpty { EMPTYUSERLIST }, QuestStatus.PUBLISHED, pageable)
        val responses = models.map { QuestListResp(it) }

        return responses
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
        val quest = findModelById(questId)
        imageService.deleteById(quest.imageId!!,authReq)
        challengeService.deleteAllByQuestId(questId,authReq)
        questHistoryRepository.deleteAllByQuestId(questId)
        missionService.deleteAllByQuestId(questId)
        rewardService.deleteAllByQuestId(questId)
        repository.delete(quest)
        return QuestDeleteResp(questId)
    }



}