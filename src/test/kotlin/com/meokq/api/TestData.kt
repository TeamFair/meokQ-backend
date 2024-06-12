package com.meokq.api

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer
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

    /**
     * emoji rank
     */
    val challenge1 = Challenge(
        challengeId = "1a1435c3-8695-45e0-aba2-05365eade0d3",
        status = ChallengeStatus.APPROVED,
        likeEmojiCnt = 3
    )
    val challenge2 = Challenge(
        challengeId = "5660fea4-6596-407c-946d-dbc3c926eb56",
        status = ChallengeStatus.APPROVED,
        likeEmojiCnt = 5
    )
    val challenge3 = Challenge(
        challengeId = "b391d3e2-f9fa-4c54-94df-5aebce941d41",
        status = ChallengeStatus.APPROVED,
        likeEmojiCnt = 4
    )
    val challenge4 = Challenge(
        challengeId = "CH10000004",
        status = ChallengeStatus.APPROVED,
        likeEmojiCnt = 5
    )
    val challenge5 = Challenge(
        challengeId = "CH10000005",
        status = ChallengeStatus.APPROVED,
        likeEmojiCnt = 0
    )

    var challengesRankTestObj = listOf(challenge1,challenge2, challenge3, challenge4, challenge5)

}