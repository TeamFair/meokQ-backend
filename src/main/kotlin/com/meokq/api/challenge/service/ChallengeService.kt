package com.meokq.api.challenge.service

import com.meokq.api.challenge.converter.ChallengeConverter
import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.specification.ChallengeSpecifications
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.request.ChallengeSearchDto
import com.meokq.api.quest.service.QuestService
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val converter : ChallengeConverter,
    private val repository: ChallengeRepository,
    private val questService : QuestService,
) : BaseService<ChallengeReq, ChallengeResp, Challenge, String> {
    override var _converter: BaseConverter<ChallengeReq, ChallengeResp, Challenge> = converter
    override var _repository: JpaRepository<Challenge, String> = repository

    fun findAll(searchDto : ChallengeSearchDto, pageable: Pageable): Page<ChallengeResp> {
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        val specification = ChallengeSpecifications.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = page.content.map{ converter.modelToResponse(it) }
        // TODO : 제목을 어떻게 보여줄지 확인필요.
        /*page.content.forEachIndexed { idx, resp ->
            // select quest
            resp.questId?.let { content[idx].quest = questService.findById(it) }
        }*/

        return PageImpl(content, pageable, page.numberOfElements.toLong())
    }

    fun review(request: ChallengeReviewReq){
        // 도전 내역 확인
        val challenge = repository.findById(request.challengeId)
            .orElseThrow { NotFoundException("challenge not found with ID: ${request.challengeId}") }

        // 도전내역 상태 변경, 거절 사유 등록
        challenge.rejectReason = request.comment
        challenge.status = request.result.status
        repository.save(challenge)

        // 승인되었다면, 쿠폰 발급
        if (request.result == ChallengeReviewResult.APPROVED){
            // TODO : 쿠폰 발급
        }
    }

    override fun deleteById(id: String) {
        val challenge = this.findById(id)

        checkNotNull(challenge.status)
        if (challenge.status.couldDelete)
            throw InvalidRequestException("You can only delete challenges that are under_review.")
        super.deleteById(id)
    }

}