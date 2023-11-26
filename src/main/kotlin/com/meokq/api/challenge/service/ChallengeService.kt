package com.meokq.api.challenge.service

import com.meokq.api.challenge.converter.ChallengeConverter
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeApproveReq
import com.meokq.api.challenge.request.ChallengeRejectReq
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.service.QuestService
import org.springframework.data.domain.Pageable
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

    fun findAllByMarketIdAndStatus(marketId: String, status: ChallengeStatus, pageable: Pageable): List<ChallengeResp> {
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

    fun approve(request: ChallengeApproveReq){
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

    fun reject(request: ChallengeRejectReq){
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