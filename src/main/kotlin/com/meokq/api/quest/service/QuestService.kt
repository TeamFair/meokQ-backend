package com.meokq.api.quest.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
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
import com.meokq.api.quiz.repository.QuizRepository
import com.meokq.api.quiz.response.QuizResp
import jakarta.persistence.EntityManager
import org.springframework.data.domain.*
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
    private val questCustomRepositoryImpl: QuestCustomRepositoryImpl,
    private val quizRepository: QuizRepository,

    ) : JpaService<Quest, String>, JpaSpecificationService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository
    override val jpaSpecRepository: BaseRepository<Quest, String> = repository
    private val specifications = QuestSpecification


    @Transactional(readOnly = true)
    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestQueryDSLListResp> {
        // 정렬 조건 정의
        val sort = Sort.by(Sort.Order.desc("score"), Sort.Order.asc("createDate"))
        val sortedPageable = PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)

        val models = questCustomRepositoryImpl.findAll(searchDto, sortedPageable)
        return PageImpl(models.content, pageable, models.totalElements)
    }

    @Transactional(readOnly = true)
    fun findAllV2(searchDto: QuestSearchDto, pageable: PageRequest): Page<QuestListResp> {
        val sort = Sort.by(Sort.Order.desc("score"), Sort.Order.asc("createDate"))
        val sortedPageable = PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)

        val models = questCustomRepositoryImpl.findAllV2(searchDto, sortedPageable)
        this.missionService.findByMissionIdInWithQuizzes(models.content.flatMap { it.missions ?: emptyList() }.toList())
        return models.map { QuestListResp(it) }
    }

    fun findById(questId: String): QuestDetailResp {
        val quest = findModelById(questId)
        missionService.findModelsByQuestId(questId).also { quest.missions = it.toMutableList() }
        rewardService.findModelsByQuestId(questId).also { quest.rewards = it.toMutableList() }
        return QuestDetailResp(quest)
    }

    fun findForCustomerById(questId: String, authReq: AuthReq): QuestCustomerResp {
        val quest = findModelById(questId)
        val challenges = this.challengeService.findLikeCountByQuestId(questId)

        var customerRank : Int? = null
        if (QuestType.REPEAT == quest.type) {
            customerRank = this.challengeService.findCustomerRank(questId, authReq.userId!!)
        }

        return QuestCustomerResp(
            quest = quest,
            topLikeChallenges = challenges,
            customerRank = customerRank,
        )
    }

    fun save(request: QuestCreateReq): QuestCreateResp {
        val modelForSave = request.toEntity()
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
        val modelForSave = request.toEntity()
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
        model.refreshFields(request.toEntity())
        saveModel(model)

        return QuestCreateResp(model)
    }

    fun count(searchDto: QuestSearchDto): Long {
        return countBy(specifications.bySearchDto(searchDto))
    }

    @Transactional(readOnly = true)
    fun getCompletedQuests(pageable: Pageable, authReq: AuthReq): Page<QuestQueryDSLListResp> {
        return questCustomRepositoryImpl.getCompletedQuests(pageable, authReq.userId!!)

    }

    @Transactional(readOnly = true)
    fun getUncompletedQuests(pageable: Pageable, authReq: AuthReq): Page<QuestQueryDSLListResp> {
//        val specification = specifications.uncompletedQuestList(authReq.userId!!)
//        val models = findAllBy(specification, pageable)
        return questCustomRepositoryImpl.getUnCompletedQuests(pageable, authReq.userId!!)
    }

    @Transactional(readOnly = true)
    fun getUncompletedEventQuests(pageable: PageRequest, authReq: AuthReq): Page<QuestQueryDSLListResp> {
        return questCustomRepositoryImpl.getUnCompletedEventQuests(pageable, authReq.userId!!)
    }

    @Transactional(readOnly = true)
    fun getUncompletedRepeatQuests(
        status: QuestTarget,
        pageable: Pageable,
        authReq: AuthReq
    ): Page<QuestQueryDSLListResp> {
        return questCustomRepositoryImpl.getUncompletedRepeatableQuests(
            questTarget = status,
            pageable = pageable,
            userId = authReq.userId!!
        )
    }

    @Transactional(readOnly = true)
    fun getUncompletedTotalQuests(
        popularYn: Boolean?,
        pageable: Pageable,
        authReq: AuthReq
    ): Page<QuestQueryDSLListResp> {
        val sort = Sort.by(Sort.Order.desc("score"), Sort.Order.asc("createDate"))
        val sortedPageable = PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)

        return questCustomRepositoryImpl.getUncompletedTotalQuests(popularYn, sortedPageable, authReq.userId!!)
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

    @Transactional(readOnly = true)
    fun findAllByReward(rewardContent: String, pageable: Pageable, authReq: AuthReq): PageImpl<QuestQueryDSLListResp> {
        return questCustomRepositoryImpl.findAllByReward(rewardContent, pageable, authReq.userId!!)
    }

}
