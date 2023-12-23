package com.meokq.api.challenge.service

import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.coupon.request.CouponReq
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
        validateQuest(challenge.questId, request.marketId)

        // 도전내역 상태 변경, 거절 사유 등록
        repository.save(challenge.apply {
            rejectReason = request.comment
            status = request.result.status
        })

        // 승인되었다면, 쿠폰 발급
        // TODO : 리워드 아이디도 포함하도록 수정
        if (request.result == ChallengeReviewResult.APPROVED){
            issueCoupon(challenge.customerId, request.marketId, challenge.challengeId)
        }
    }

    /**
     * 해당 마켓에서 등록한 퀘스트가 맞는지 확인
     */
    private fun validateQuest(questId: String?, marketId: String) {
        checkNotNull(questId) { "Quest ID must not be null" }

        val quest = questService.findById(questId)
        if (quest.marketId != marketId) {
            throw IllegalStateException("Only quests registered in the relevant market can be evaluated.")
        }
    }

    private fun issueCoupon(customerId: String?, marketId: String, challengeId: String?) {
        checkNotNull(customerId) { "Customer ID must not be null" }
        checkNotNull(challengeId) { "Challenge ID must not be null" }

        couponService.save(
            CouponReq(
                targetUserId = customerId,
                targetMarketId = marketId,
                challengeId = challengeId
            )
        )
    }
}