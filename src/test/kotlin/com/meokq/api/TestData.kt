package com.meokq.api

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.market.enums.WeekDay
import com.meokq.api.market.model.Market
import com.meokq.api.market.request.MarketReq
import com.meokq.api.market.request.MarketTimeReq
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.service.QuestService
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import org.springframework.mock.web.MockMultipartFile
import java.util.*

object TestData {
    /**
     * authReq
     */
    val authReqCS10000001 = AuthReq(
        userId = "CS10000001",
        userType = UserType.CUSTOMER,
    )

    val authReqBS10000001 = AuthReq(
        userId = "BS10000001",
        userType = UserType.BOSS,
    )

    val authReqAdmin = AuthReq(
        userId = "admin",
        userType = UserType.ADMIN,
    )

    /**
     * loginReq
     */
    val loginReqCustomerForSave = LoginReq(
        userType = UserType.CUSTOMER,
        accessToken = "accessToken",
        refreshToken = null,
        email = UUID.randomUUID().toString(),
        channel = AuthChannel.KAKAO
    )

    val loginReqBossForSave = LoginReq(
        email = UUID.randomUUID().toString(),
        channel = AuthChannel.APPLE,
        accessToken = "accessToken",
        refreshToken = null,
        userType = UserType.BOSS,
    )

    val loginReqBS10000001 = LoginReq(
        userType = UserType.BOSS,
        accessToken = "accessToken",
        refreshToken = null,
        email = "user1@example.com",
        channel = AuthChannel.KAKAO,
    )

    val loginReqCS10000001 = LoginReq(
        userType = UserType.CUSTOMER,
        accessToken = "accessToken",
        refreshToken = null,
        email = "user1@example.com",
        channel = AuthChannel.KAKAO,
    )

    val loginReqAdmin = LoginReq(
        email = "admin",
        channel = AuthChannel.KAKAO,
        accessToken = "accessToken",
        refreshToken = null,
        userType = UserType.ADMIN,
    )

    /**
     * user
     */
    val bossBS10000001 = Boss(
        bossId = "BS10000001",
        email = "user1@example.com",
    )

    val customerCS10000001 = Customer(
        customerId = "CS10000001",
        email = "user1@example.com",
        nickname = "nickname1",
    )

    /**
     * mission
     */
    val missionReqForSave1 = MissionReq(
        content = null,
        target = "COFFEE",
        quantity = 1,
        type = MissionType.NORMAL,
    )

    val missionReqForSave2 = MissionReq(
        content = "매장 방문 5회",
        target = null,
        quantity = null,
        type = MissionType.FREE,
    )

    val missionForSave = Mission(
        questId = "QS20000001",
        quantity = 5,
        content = null,
        target = "TEA",
        type = MissionType.NORMAL,
    )

    /**
     * reward
     */
    val rewardReqForSave1 = RewardReq(
        target = "COFFEE",
        quantity = null,
        type = RewardType.DISCOUNT,
        discountRate = 90,
        content = null
    )

    val rewardReqForSave2 = RewardReq(
        target = "TEA",
        quantity = 1,
        type = RewardType.GIFT,
        discountRate = null,
        content = null
    )

    val rewardReqForSave3 = RewardReq(
        target = "TEA",
        quantity = 50,
        type = RewardType.XP,
        discountRate = null,
        content = null
    )

    /**
     * testFile
     */
    val testFileKey = "test-image-${UUID.randomUUID()}.png"
    val testFile = MockMultipartFile(
        "test-image.png",
        "test-image.png",
        "image/png",
        "Hello, World!".toByteArray()
    )

    /**
     * questCreateReqForAdmin
     */
    val questCreateReqForAdmin = QuestCreateReqForAdmin(
        missions = listOf(missionReqForSave1, missionReqForSave2),
        rewards = listOf(rewardReqForSave1),
        writer = "일상 테스트 작성자",
        imageId = "IM10000001",
        expireDate = "2024-12-31"
    )


    fun saveBoss(bossService: BossService): Boss {
        val loginReq = LoginReq(
            email = "${UUID.randomUUID()}@test.com",
            channel = AuthChannel.KAKAO,
            accessToken = "",
            refreshToken = "",
            userType = UserType.BOSS
        )

        val boss = bossService.registerMember(loginReq)
        return bossService.findModelById(boss.userId!!)
    }

    fun saveCustomer(customerService: CustomerService): Customer{
        val loginReq = LoginReq(
            email = "",
            channel = AuthChannel.KAKAO,
            accessToken = "",
            refreshToken = "",
            userType = UserType.CUSTOMER,
        )

        loginReq.email = "${UUID.randomUUID()}@test.com"

        val customer = customerService.registerMember(loginReq)
        return customerService.findModelById(customer.userId!!)
    }

    fun saveMarket(marketService: MarketService, boss: Boss): Market {
        val marketReq = MarketReq(
            logoImageId = "IM1001",
            name = "market-test",
            district = "1111010200",
            address = "서울시 00구 00동",
            phone = "01011112222",
            marketTime = listOf(
                MarketTimeReq(weekDay = WeekDay.MON, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.TUE, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.WED, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.THU, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.FRI, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.SAT, openTime = "0900", closeTime = "1800", holidayYn = TypeYN.N),
                MarketTimeReq(weekDay = WeekDay.SUN, openTime = "0000", closeTime = "0000", holidayYn = TypeYN.Y),
            )
        )

        val authReq = AuthReq(
            userType = UserType.BOSS,
            userId = boss.bossId
        )

        val market = marketService.saveMarket(marketReq, authReq)
        return marketService.findModelById(market.marketId!!)
    }

    fun saveQuest(
        questService: QuestService,
        market: Market,
        missions: List<MissionReq> = listOf(missionReqForSave1),
        rewards: List<RewardReq> = listOf(rewardReqForSave3)
    ): Quest {
        val questCreateReq = QuestCreateReq(
            marketId = market.marketId!!,
            missions = missions,
            rewards = rewards,
        )
        val questResp = questService.save(questCreateReq)
        return questService.findModelById(questResp.questId!!)
    }

    fun saveChallenge(challengeService: ChallengeService, quest: Quest, customer: Customer) : Challenge {
        val challengeSaveReq = ChallengeSaveReq(
            questId = quest.questId!!,
            receiptImageId = "IIM001"
        )
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = customer.customerId,
        )
        val resp = challengeService.save(challengeSaveReq, authReq)
        return challengeService.findModelById(resp.challengeId!!)
    }




}