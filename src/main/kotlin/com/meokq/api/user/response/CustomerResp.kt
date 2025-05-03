package com.meokq.api.user.response

import com.meokq.api.title.model.Title
import com.meokq.api.title.response.TitleResp
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Customer-Response")
data class CustomerResp(
    val status: UserStatus,
    val nickname: String?,
    val couponCount: Long?,
    val completeChallengeCount: Long?,
    val xpPoint: Long?,
    val profileImage: String?,
    val title: TitleResp?,
){
    constructor(model: Customer, couponCount: Long, challengeCount: Long, title: Title?) : this(
        status = model.status,
        nickname = model.nickname,
        couponCount = couponCount,
        completeChallengeCount = challengeCount,
        xpPoint = model.totalXp(),
        profileImage = model.profileImageId,
        title = title?.let { TitleResp(title) },
    )

    constructor(customer: Customer, title: Title) : this(
        status = customer.status,
        nickname = customer.nickname,
        couponCount = null,
        completeChallengeCount = null,
        xpPoint = null,
        profileImage = customer.profileImageId,
        title = TitleResp(title),
    )
}
