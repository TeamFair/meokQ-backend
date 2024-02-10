package com.meokq.api

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer
import java.util.*

object TestData {
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

    val loginReqCustomerForSave = LoginReq(
        userType = UserType.CUSTOMER,
        accessToken = "accessToken",
        refreshToken = null,
        email = UUID.randomUUID().toString(),
        channel = AuthChannel.KAKAO
    )

    val loginReqCS10000001 = LoginReq(
        userType = UserType.CUSTOMER,
        accessToken = "accessToken",
        refreshToken = null,
        email = "user1@example.com",
        channel = AuthChannel.KAKAO,
    )

    val loginReqBossForSave = LoginReq(
        email = UUID.randomUUID().toString(),
        channel = AuthChannel.APPLE,
        accessToken = "accessToken",
        refreshToken = null,
        userType = UserType.BOSS,
    )

    val bossBS10000001 = Boss(
        bossId = "BS10000001",
        email = "user1@example.com",
    )

    val customerCS10000001 = Customer(
        customerId = "CS10000001",
        email = "user1@example.com",
        nickname = "nickname1",
    )
}