package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.auth.filters.JwtFilter
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.market.model.Market
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.model.Quest
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
class QuestBaseTest {
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

    lateinit var testBoss: Boss
    lateinit var testCustomer01: Customer
    lateinit var testCustomer02: Customer
    lateinit var testMarket: Market
    lateinit var testOtherMarket: Market
    lateinit var testQuest01: Quest
    lateinit var testChallenge01: Challenge

    @BeforeEach
    fun initData(){
        testBoss = TestData.saveBoss(bossService).copy()
        testCustomer01 = TestData.saveCustomer(customerService).copy()
        testCustomer02 = TestData.saveCustomer(customerService).copy()
        testMarket = TestData.saveMarket(marketService, testBoss)
        testOtherMarket = TestData.saveMarket(marketService, testBoss)
        testQuest01 = TestData.saveQuest(questService, testMarket)
        testChallenge01 = TestData.saveChallenge(challengeService, testQuest01, testCustomer01).copy()


        setSecurityContext(TestData.authReqCS10000001)

        questService.adminSave(TestData.questCreateReqForAdmin)

    }

    fun setSecurityContext(authReq: AuthReq) {
        jwtFilter.setSecurityContext(authReq)
    }



}