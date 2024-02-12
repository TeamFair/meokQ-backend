package com.meokq.api

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
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
}