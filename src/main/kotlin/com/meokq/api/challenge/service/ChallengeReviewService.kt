package com.meokq.api.challenge.service

import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.challenge.response.ChallengeReviewResp
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.request.CouponSaveReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.repository.RewardRepository
import com.meokq.api.quest.service.QuestService
import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.processor.UserAction
import org.springframework.stereotype.Service

@Service
class ChallengeReviewService(
    private val repository: ChallengeRepository,
    private val couponService: CouponService,
    private val questService: QuestService,
    private val rewardRepository: RewardRepository,
    private val customerService: CustomerService
){
    fun review(request: ChallengeReviewReq): ChallengeReviewResp{
        // 도전 내역이 존재하는지 확인
        val challenge = repository.findById(request.challengeId)
            .orElseThrow { NotFoundException("challenge not found with ID: ${request.challengeId}") }

        // 해당 마켓에서 등록한 퀘스트가 맞는지 확인
        checkNotNull(challenge.questId) { "Quest ID must not be null" }
        val quest = questService.findModelById(challenge.questId!!)
        if (quest.marketId != request.marketId) {
            throw InvalidRequestException("Only quests registered in the relevant market can be evaluated.")
        }

        // 결과 처리
        if (request.result == ChallengeReviewResult.APPROVED){
            registerReviewResult(challenge, request)
            return approveChallenge(challenge, quest)

        } else if (request.result == ChallengeReviewResult.REJECTED){
            return rejectChallenge(challenge, quest, request)

        } else {
            throw InvalidRequestException("유효하지 않은 승인 결과입니다.")

        }
    }

    private fun approveChallenge(challenge: Challenge, quest: Quest): ChallengeReviewResp{
        val rewards = rewardRepository.findAllByQuestId(quest.questId!!)
        val coupons = releaseCoupon(rewards, challenge, quest)
        getXp(rewards, challenge)

        // result
        return ChallengeReviewResp(
            challengeId = challenge.challengeId,
            coupons = coupons.map(::CouponResp)
        )
    }

    private fun registerReviewResult(challenge: Challenge, request: ChallengeReviewReq){
        repository.save(challenge.apply {
            rejectReason = request.comment
            status = request.result.status
        })
    }

    private fun releaseCoupon(rewards: List<Reward>, challenge: Challenge, quest: Quest): List<Coupon> {
        val couponReqList = rewards
            .filter { it.type?.releaseCoupon == true }
            .map {
                CouponSaveReq(
                    challengeId = challenge.challengeId,
                    questId = quest.questId,
                    marketId = quest.marketId,
                    userId = challenge.customerId,
                    rewardId = it.rewardId!!,
                )
            }

        if (couponReqList.isEmpty()) return listOf()
        val coupons = couponService.saveAll(couponReqList)
        return coupons
    }

    private fun getXp(rewards: List<Reward>, challenge: Challenge) {
        rewards
            .filter { it.type == RewardType.XP }
            .map {
                customerService.gainXp(challenge.customerId!!,UserAction.CHALLENGE_REGISTER)
            }
    }

    private fun rejectChallenge(challenge: Challenge, quest: Quest, request: ChallengeReviewReq): ChallengeReviewResp{
        registerReviewResult(challenge, request)
        val reward = rewardRepository.findAllByQuestId(quest.questId!!)
        returnXp(reward, challenge)
        // result
        return ChallengeReviewResp(
            challengeId = challenge.challengeId,
        )
    }

    private fun returnXp(rewards: List<Reward>, challenge: Challenge) {
        rewards
            .filter { it.type == RewardType.XP }
            .map {
                customerService.returnXp(challenge.customerId!!,UserAction.CHALLENGE_DELETE)
            }
    }


}