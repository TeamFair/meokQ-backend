package com.meokq.api.application.service

import com.meokq.api.application.enums.ChallengeStatus
import com.meokq.api.application.model.Challenge
import com.meokq.api.application.repository.ChallengeRepository
import com.meokq.api.application.request.ChallengeApproveRequest
import com.meokq.api.application.request.ChallengeRejectRequest
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.ChallengeConverter
import com.meokq.api.core.exception.advice.NotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val converter : ChallengeConverter,
    private val repository: ChallengeRepository,
    private val questService : QuestService,
) : BaseService<ChallengeRequest, ChallengeResponse, Challenge, String> {
    override var _converter: BaseConverter<ChallengeRequest, ChallengeResponse, Challenge> = converter
    override var _repository: JpaRepository<Challenge, String> = repository

    fun findAllByMarketIdAndStatus(marketId: String, status: ChallengeStatus, pageable: Pageable): List<ChallengeResponse> {
        // select challenge-history
        val result = repository.findAllByMarketIdAndStatus(marketId, status, pageable)
        val responseList = converter.modelToResponse(result)
        result.forEachIndexed { idx, resp ->
            // select quest
            val questId = resp.questId ?: throw NotFoundException("quest id is null!")
            responseList[idx].quest = questService.findById(questId)
        }
        return responseList
    }

    fun approve(request: ChallengeApproveRequest){
        // 도전 내역 확인
        val cnt = repository.countByChallengeIdAndMarketId(
            challengeId = request.challengeId,
            marketId = request.marketId)
        if (cnt < 1) throw NotFoundException("request is not found!!")

        // 도전내역 상태 변경
        repository.updateRejectReasonAndStatus(
            challengeId = request.challengeId,
            status = ChallengeStatus.APPROVED,
            rejectReason = null
        )

        // 쿠폰 발급

    }

    fun reject(request: ChallengeRejectRequest){
        // 도전 내역 확인
        val cnt = repository.countByChallengeIdAndMarketId(
            challengeId = request.challengeId,
            marketId = request.marketId)
        if (cnt < 1) throw NotFoundException("request is not found!!")

        // 도전내역 상태 변경, 거절 사유 등록
        repository.updateRejectReasonAndStatus(
            challengeId = request.challengeId,
            status = ChallengeStatus.REJECTED,
            rejectReason = request.rejectReason
        )
    }

}