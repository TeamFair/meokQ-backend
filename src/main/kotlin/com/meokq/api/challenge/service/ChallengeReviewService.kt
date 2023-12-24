package com.meokq.api.challenge.service

import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.coupon.request.CouponSaveReq
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.quest.service.QuestService
import org.springframework.stereotype.Service

@Service
class ChallengeReviewService(
    private val repository: ChallengeRepository,
    private val couponService: CouponService,
    private val questService: QuestService,
) {
    fun review(request: ChallengeReviewReq){
        // 도전 내역이 존재하는지 확인
        val challenge = repository.findById(request.challengeId)
            .orElseThrow { NotFoundException("challenge not found with ID: ${request.challengeId}") }

        // 해당 마켓에서 등록한 퀘스트가 맞는지 확인
        checkNotNull(challenge.questId) { "Quest ID must not be null" }
        val quest = questService.findModelById(challenge.questId!!)
        if (quest.marketId != request.marketId) {
            throw IllegalStateException("Only quests registered in the relevant market can be evaluated.")
        }

        // 도전내역 상태 변경, 거절 사유 등록
        repository.save(challenge.apply {
            rejectReason = request.comment
            status = request.result.status
        })

        // 승인되었다면, 쿠폰 발급
        if (request.result == ChallengeReviewResult.APPROVED){
            couponService.saveAll(
                CouponSaveReq(
                    challengeId = challenge.challengeId,
                    questId = challenge.questId,
                    marketId = quest.marketId,
                    userId = challenge.customerId
                )
            )
        }
    }
}