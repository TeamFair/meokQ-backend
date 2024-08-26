package com.meokq.api.challenge.service

import com.meokq.api.TestData
import com.meokq.api.auth.filters.JwtFilter
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.model.Image
import com.meokq.api.file.repository.ImageRepository
import com.meokq.api.file.request.ImageReq
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
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
class ChallengeBaseTest {
    @Autowired
    lateinit var challengeReviewService: ChallengeReviewService
    @Autowired
    lateinit var challengeService: ChallengeService
    @Autowired
    lateinit var couponService: CouponService
    @Autowired
    lateinit var marketService: MarketService
    @Autowired
    lateinit var bossService: BossService
    @Autowired
    lateinit var customerService: CustomerService
    @Autowired
    lateinit var questService: QuestService
    @Autowired
    lateinit var jwtFilter: JwtFilter
    @Autowired
    lateinit var imageRepository: ImageRepository


    lateinit var testBoss: Boss
    lateinit var testCustomer01: Customer
    lateinit var testCustomer02: Customer
    lateinit var testMarket: Market
    lateinit var testOtherMarket: Market
    lateinit var testQuest01: Quest
    lateinit var testQuestXp: Quest // reward type = xp
    lateinit var testChallenge01: Challenge
    lateinit var testChallengeXp: Challenge
    lateinit var testAdminQuestXp: Quest // reward type = xp
    lateinit var testAdminChallenge: Challenge



    @BeforeEach
    fun initData(){
        testBoss = TestData.saveBoss(bossService).copy()
        testCustomer01 = TestData.saveCustomer(customerService).copy()

        Thread.sleep(100)
        testCustomer02 = TestData.saveCustomer(customerService).copy()
        testMarket = TestData.saveMarket(marketService, testBoss)
        testOtherMarket = TestData.saveMarket(marketService, testBoss)

        val rewardReqForSave2 = RewardReq(
            target = "TEA",
            quantity = 1,
            type = RewardType.GIFT,
            discountRate = null,
            content = null
        )

        imageRepository.save(Image(ImageReq(ImageType.QUEST_IMAGE, TestData.testFile),"IM10000001"))
        imageRepository.save(Image(ImageReq(ImageType.QUEST_IMAGE, TestData.testFile),"IIM001"))

        testQuest01 = TestData.saveQuest(questService, testMarket, rewards = listOf(rewardReqForSave2))
        testChallenge01 = TestData.saveChallenge(challengeService, testQuest01, testCustomer01).copy()

        jwtFilter.setSecurityContext(TestData.authReqAdmin)

        val xpReward = RewardReq(content = "STRENGTH", target = "", quantity = 20, discountRate = 0, type = RewardType.XP)
        testQuestXp = TestData.saveQuest(questService, testMarket, rewards = listOf(xpReward))
        testAdminQuestXp = TestData.saveAdminQuest(questService, rewards = listOf(xpReward))
        testChallengeXp = TestData.saveChallenge(challengeService, testQuestXp, testCustomer01)
        testAdminChallenge = TestData.saveChallenge(challengeService, testAdminQuestXp, testCustomer01)

    }

    fun setSecurityContext(authReq: AuthReq) {
        jwtFilter.setSecurityContext(authReq)
    }
}