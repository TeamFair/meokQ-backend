package com.meokq.api.challenge.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.converter.ChallengeConverter
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.response.ReadChallengeResp
import com.meokq.api.challenge.specification.ChallengeSpecifications
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.image.service.ImageService
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.service.QuestService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChallengeService(
    private val repository: ChallengeRepository,
    private val questService: QuestService,
    private val imageService: ImageService,
    //private val customerService: CustomerService,

    ) : BaseService<ChallengeReq, ChallengeResp, Challenge, String> {
    @Deprecated("사용하지 않음.")
    override var _converter: BaseConverter<ChallengeReq, ChallengeResp, Challenge> = ChallengeConverter
    override var _repository: JpaRepository<Challenge, String> = repository

    @Transactional
    fun save(request: ChallengeSaveReq, authReq: AuthReq) : Challenge{
        val userId = authReq.userId

        with(request){
            checkNotNullData(questId, "quest-id is null")
            checkNotNullData(receiptImageId, "receipt-image-id is null")
            checkNotNullData(userId, "user-id is null")

            val quest = questService.findModelById(questId)
            val receiptImage = imageService.findModelById(receiptImageId)
            //val customer = userId?.let { customerService.findModelById(it) }

            val challenge = Challenge(
                //customer = customer,
                customerId = authReq.userId,
                status = ChallengeStatus.UNDER_REVIEW,
                //quest = quest,
                questId = quest.questId,
                //receiptImage = receiptImage
                receiptImageId = receiptImage.fileId
            )

            return repository.save(challenge)
        }
    }

    fun findAll(
        searchDto : ChallengeSearchDto,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<ReadChallengeResp> {
        if (authReq.userType == UserType.CUSTOMER && searchDto.userDataOnly) {
            searchDto.userId = authReq.userId
        }

        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val specification = ChallengeSpecifications.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = page.content.map {
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
        return PageImpl(content, pageable, count)
    }

    override fun findById(id: String): ChallengeResp {
        val challenge = repository.findById(id).orElseThrow{NotFoundException("challenge not found with ID: $id")}
        val challengeResp = _converter.modelToResponse(challenge).apply {
            quest = challenge.challengeId?.let { QuestResp(questService.findModelById(it)) }
        }

        return challengeResp
    }

    override fun deleteById(id: String, authReq: AuthReq) {
        val challenge = findModelById(id)

        checkNotNull(challenge.status)
        if (challenge.customerId != authReq.userId)
            throw InvalidRequestException("도전내역을 등록한 계정과 현재 계정이 다릅니다.")

        if (challenge.status.couldDelete)
            throw InvalidRequestException("You can only delete challenges that are under_review.")

        super.deleteById(id)
    }

    fun count(searchDto: ChallengeSearchDto): Long {
        val specification = ChallengeSpecifications.bySearchDto(searchDto)
        return repository.count(specification)
    }

}