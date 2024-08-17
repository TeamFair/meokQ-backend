package com.meokq.api.challenge.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.response.ReadChallengeResp
import com.meokq.api.challenge.specification.ChallengeSpecifications
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.exception.AccessDeniedException
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.emoji.repository.EmojiRepository
import com.meokq.api.emoji.response.EmojiResp
import com.meokq.api.file.service.ImageService
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.service.QuestHistoryService
import com.meokq.api.quest.service.QuestService
import com.meokq.api.quest.service.RewardService
import com.meokq.api.rank.ChallengeEmojiRankService
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.service.AdminService
import com.meokq.api.user.service.CustomerService
import org.aspectj.weaver.ast.Not
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChallengeService(
    private val repository: ChallengeRepository,
    private val questHistoryService: QuestHistoryService,
    private val customerService: CustomerService,
    private val adminService: AdminService,
    private val emojiRepository: EmojiRepository,
    private val challengeEmojiRankService: ChallengeEmojiRankService,
    private val rewardService: RewardService,
    private val imageService: ImageService,
    private val questRepository : QuestRepository,

    ) : JpaService<Challenge, String>, JpaSpecificationService<Challenge, String> {

    override var jpaRepository: JpaRepository<Challenge, String> = repository
    override val jpaSpecRepository: BaseRepository<Challenge, String> = repository
    private val specifications = ChallengeSpecifications

    @Transactional
    fun save(request: ChallengeSaveReq, authReq: AuthReq): Challenge {
        checkNotNullData(request.questId, "quest-id is null")
        checkNotNullData(request.receiptImageId, "receipt-image-id is null")
        checkNotNullData(authReq.userId, "user-id is null")

        // 20240519 어드민이 등록한 퀘스트는 자동 승인 처리 됌.
        val quest = questRepository.findById(request.questId)
            .orElseThrow{NotFoundException("quest not found with ID: ${request.questId}")}
        val isAdminQuest = quest.creatorRole?.let { adminService.exit(it.authorization) } ?: false
        var status = ChallengeStatus.UNDER_REVIEW
        if (isAdminQuest) {
            status = ChallengeStatus.APPROVED
        }
        questHistoryService.save(quest.questId!!, authReq.userId!!)

        val model = Challenge(request)
        model.customerId = authReq.userId
        model.status = status
        val result = saveModel(model)

        challengeEmojiRankService.addToRank(model)
        rewardService.gainRewardByQuestId(request.questId, authReq.userId)

        return result
    }

    fun findAll(
        searchDto: ChallengeSearchDto,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<ReadChallengeResp> {
        customizeSearchDto(searchDto, authReq)
        val specification = specifications.joinAndFetch(searchDto)
        val models = findAllBy(specification, pageable)
        models.forEach(::updateEmojiCnt)
        val responses = models.content.map(::convertModelToResp)
        return PageImpl(responses, pageable, models.totalElements)
    }

    private fun customizeSearchDto(searchDto: ChallengeSearchDto, authReq: AuthReq) {
        if (authReq.userType == UserType.CUSTOMER && searchDto.userDataOnly) {
            searchDto.userId = authReq.userId
        }
    }

    private fun convertModelToResp(model: Challenge): ReadChallengeResp {
        val response = ReadChallengeResp(model)
        try {
            response.quest = model.questId?.let { questId ->
                QuestResp(questRepository.findById(questId)
                    .orElseThrow{NotFoundException("quest not found with ID: ${questId}")})
            }
        } catch (e: NotFoundException) {
            response.quest = null
        }
        model.customerId?.let { customerId ->
       //     val customer = customerService.findById(customerId)
         //   customer.ifPresent { response.userNickName = it.nickname }
        }

        return response
    }

    private fun updateEmojiCnt(model: Challenge) {
        val emojis = emojiRepository.findByTargetId(model.challengeId!!)
        val emojiResp = EmojiResp(emojis)
        model.appendEmojiCnt(emojiResp)
        saveModel(model)
    }

    fun findById(id: String): ChallengeResp {
        val model = findModelById(id)
        val quest = model.questId?.let { questRepository.findById(it)
            .orElseThrow{NotFoundException("quest not found with ID: ${it}")}}
        return ChallengeResp(model, quest)
    }

    @Transactional
    fun delete(challengeId: String, authReq: AuthReq) {
        val challenge = findModelById(challengeId)
        checkNotNull(challenge.status)
        if(challenge.customerId != authReq.userId && authReq.userType == UserType.CUSTOMER){
            throw AccessDeniedException("챌린지 생성자 아니면 삭제할 수 없습니다.")
        }
        challenge.status.deleteAction()
        questRepository.findById(challenge.questId!!)
            .ifPresent {
                it.rewards?.let {
                    it.filter { reward -> reward.type == RewardType.XP }
                        .forEach { reward ->
                            customerService.returnXp(
                                challenge.customerId!!,
                                reward.quantity?.toLong()!!
                            )
                        }
                }
            }

        imageService.deleteById(challenge.receiptImageId!!, authReq)
        challengeEmojiRankService.deleteFromRank(challenge)
        emojiRepository.deleteAllByTargetId(challenge.challengeId!!)

        deleteById(challengeId)

    }

    fun deleteAllByQuestId(questId: String, authReq: AuthReq) {
        val challenges = repository.findAllByQuestId(questId)
        challenges.forEach {
            imageService.deleteById(it.receiptImageId!!, authReq)
            challengeEmojiRankService.deleteFromRank(it)
            deleteById(it.challengeId!!)
        }
    }

    fun count(searchDto: ChallengeSearchDto): Long {
        val specification = specifications.joinAndFetch(searchDto)
        return countBy(specification)
    }

    @Transactional(readOnly = true)
    fun findRandomAll(pageable: Pageable): Page<ReadChallengeResp> {
        val randomModels = challengeEmojiRankService.fetchShuffleRankToPage(pageable.pageNumber, pageable.pageSize)
        val responses = randomModels.map(::convertModelToResp)
        val count = repository.count()
        return PageImpl(responses, pageable, count)
    }

    fun increaseViewCount(id: String, authReq: AuthReq) : ReadChallengeResp {
        val challenge = findModelById(id)
        // 도전내역 등록자와 현재 사용자가 같을 경우 조회수를 증가하지 않는다
        if (authReq.userId != challenge.customerId) {
            challenge.increaseViewCount()
        }
        return ReadChallengeResp(saveModel(challenge))
    }

    fun updateRank(challengeId: String){
        val challenge = findModelById(challengeId)
        challengeEmojiRankService.addToRank(challenge)
    }


    // 어플리케이션 시작시 challenge 이모지 순위 동기화
    @Transactional
    fun syncRank() {
        val emojis = emojiRepository.findAll()
        val challenges = repository.findAllByStatus(ChallengeStatus.APPROVED)

        val groupedEmojis = emojis.groupBy { it.targetId }

        challenges.forEach { target ->
            val targetEmojis = groupedEmojis[target.challengeId] ?: emptyList()
            val emojiResps = EmojiResp(targetEmojis)
            target.appendEmojiCnt(emojiResps)
            challengeEmojiRankService.addToRank(target)
        }
    }


}