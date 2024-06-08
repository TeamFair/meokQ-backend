package com.meokq.api.challenge.service

import com.meokq.api.TestData.saveBoss
import com.meokq.api.TestData.saveChallenge
import com.meokq.api.TestData.saveCustomer
import com.meokq.api.TestData.saveMarket
import com.meokq.api.TestData.saveQuest
import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.market.model.Market
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.service.QuestService
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class ChallengeReviewServiceTest{

    @Autowired private lateinit var challengeReviewService: ChallengeReviewService
    @Autowired private lateinit var challengeService: ChallengeService
    @Autowired private lateinit var couponService: CouponService
    @Autowired private lateinit var marketService: MarketService
    @Autowired private lateinit var bossService: BossService
    @Autowired private lateinit var customerService: CustomerService
    @Autowired private lateinit var questService: QuestService

    private lateinit var testBoss: Boss
    private lateinit var testCustomer: Customer
    private lateinit var testMarket: Market
    private lateinit var testOtherMarket: Market
    private lateinit var testQuest01: Quest
    private lateinit var testQuestXp: Quest // reward type = xp
    private lateinit var testChallenge01: Challenge
    private lateinit var testChallengeXp: Challenge

    @BeforeEach
    fun initData(){
        testBoss = saveBoss(bossService).copy()
        testCustomer = saveCustomer(customerService).copy()
        testMarket = saveMarket(marketService, testBoss)
        testOtherMarket = saveMarket(marketService, testBoss)
        testQuest01 = saveQuest(questService, testMarket)
        testChallenge01 = saveChallenge(challengeService, testQuest01, testCustomer).copy()

        val xpReward = RewardReq(content = "", target = "", quantity = 20, discountRate = 0, type = RewardType.XP)
        testQuestXp = saveQuest(questService, testMarket, rewards = listOf(xpReward))
        testChallengeXp = saveChallenge(challengeService, testQuestXp, testCustomer)
    }

    @Test
    @DisplayName("도전내역을 정상적으로 승인합니다.")
    fun `review-approve`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = testChallenge01.challengeId!!,
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = testMarket.marketId!!
        )

        // when
        val resp = challengeReviewService.review(request)

        // then
        val challenge = challengeService.findModelById(request.challengeId)
        Assertions.assertEquals(request.challengeId, challenge.challengeId)
        Assertions.assertEquals(request.result.status, challenge.status)
    }

    @Test
    @DisplayName("도전내역을 정상적으로 승인합니다.: 쿠폰이 정상적으로 발급되었습니다.")
    fun `review-approve-coupon`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = testChallenge01.challengeId!!,
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = testMarket.marketId!!
        )

        // when
        challengeReviewService.review(request)

        // then
        val couponList = couponService.findAllByChallengeId(request.challengeId)
        val couponListForChallenge = couponList.stream().filter{it.challengeId==request.challengeId}.toList()
        Assertions.assertTrue(couponListForChallenge.isNotEmpty())
    }

    @Test
    @DisplayName("도전내역을 정상적으로 거부합니다.")
    fun `review-reject`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = testChallenge01.challengeId!!,
            result = ChallengeReviewResult.REJECTED,
            comment = "테스트입니다.",
            marketId = testMarket.marketId!!
        )

        // when
        challengeReviewService.review(request)

        // when
        val challenge = challengeService.findModelById(request.challengeId)
        Assertions.assertEquals(request.challengeId, challenge.challengeId)
        Assertions.assertEquals(request.result.status, challenge.status)
    }

    @Test
    @DisplayName("도전내역-검토결과등록에 실패하였습니다.: 마켓에서 등록한 퀘스트가 아닐때")
    fun `review-approve-invaild-market`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = testChallenge01.challengeId!!,
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = testOtherMarket.marketId!!
        )

        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            challengeReviewService.review(request)
        }
    }

    @Test
    @DisplayName("도전내역-검토결과등록에 실패하였습니다.: 존재하지 않는 도전내역")
    fun `review-invalid-challenge`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH00000000",
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = "MK00000002"
        )

        // when
        Assertions.assertThrows(NotFoundException::class.java){
            challengeReviewService.review(request)
        }
    }

    @Test
    @DisplayName("XP타입의 보상을 승인합니다.")
    fun approveXpReward(){
        // given
        val request = ChallengeReviewReq(
            challengeId = testChallengeXp.challengeId!!,
            result = ChallengeReviewResult.APPROVED,
            comment = "테스트입니다.",
            marketId = testMarket.marketId!!
        )

        // when
        val resp = challengeReviewService.review(request)

        // when
        val challenge = challengeService.findModelById(request.challengeId)
        Assertions.assertEquals(request.challengeId, challenge.challengeId)
        Assertions.assertEquals(request.result.status, challenge.status)
        Assertions.assertEquals(challenge.challengeId, resp.challengeId)
        Assertions.assertTrue { resp.coupons.isEmpty() }
    }
}