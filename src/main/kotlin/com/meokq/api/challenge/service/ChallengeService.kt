package com.meokq.api.challenge.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
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
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.service.QuestService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val repository: ChallengeRepository,
    private val questService: QuestService,
    ) : JpaService<Challenge, String>, JpaSpecificationService<Challenge, String> {

    override var jpaRepository: JpaRepository<Challenge, String> = repository
    override val jpaSpecRepository: BaseRepository<Challenge, String> = repository
    private val specifications = ChallengeSpecifications

    fun save(request: ChallengeSaveReq, authReq: AuthReq) : Challenge {
        checkNotNullData(request.questId, "quest-id is null")
        checkNotNullData(request.receiptImageId, "receipt-image-id is null")
        checkNotNullData(authReq.userId, "user-id is null")

        val model = Challenge(request)
        model.customerId = authReq.userId
        return saveModel(model)
    }

    fun findAll(
        searchDto : ChallengeSearchDto,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<ReadChallengeResp> {
        if (authReq.userType == UserType.CUSTOMER && searchDto.userDataOnly) {
            searchDto.userId = authReq.userId
        }

        val specification = specifications.joinAndFetch(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.map {
            val resp = ReadChallengeResp(it)
            try {
                resp.quest = it.questId?.let { questId ->
                    QuestResp(questService.findModelById(questId))
                }
            } catch (e : Exception){
                e.printStackTrace()
            }
            resp
        }

        val count = repository.count(specification)
        return PageImpl(responses, pageable, count)
    }

    fun findById(id: String): ChallengeResp {
        val model = findModelById(id)
        val quest = model.questId?.let { questService.findModelById(it) }
        return ChallengeResp(model, quest)
    }

     fun delete(id: String, authReq: AuthReq) {
        val challenge = findModelById(id)

        checkNotNull(challenge.status)
        if (challenge.customerId != authReq.userId)
            throw InvalidRequestException("도전내역을 등록한 계정과 현재 계정이 다릅니다.")

        if (!challenge.status.couldDelete)
            throw InvalidRequestException("You can only delete challenges that are under_review.")

        deleteById(id)
    }

    fun count(searchDto: ChallengeSearchDto): Long {
        val specification = specifications.joinAndFetch(searchDto)
        return repository.count(specification)
    }

}