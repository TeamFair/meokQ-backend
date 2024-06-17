package com.meokq.api.user.response

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Customer-Response")
data class CustomerResp(
    val status: UserStatus,
    val nickname: String?,
    val couponCount: Long,
    val completeChallengeCount: Long,
    val xpPoint: Long
){
    constructor(model : Customer, couponCount: Long, challengeCount: Long) : this(
        status = model.status,
        nickname = model.nickname,
        couponCount = couponCount,
        completeChallengeCount = challengeCount,
        xpPoint = model.xpPoint ?: 0
    )
}
