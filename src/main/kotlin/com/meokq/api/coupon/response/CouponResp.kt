package com.meokq.api.coupon.response

import com.meokq.api.core.converter.DateTimeConverterV2.convertToString
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.response.MissionResp
import com.meokq.api.quest.response.RewardResp

data class CouponResp(
    val useDate: String?,
    val status: CouponStatus?,
    val couponId: String?,
    val expireDate: String?,
    val marketId: String?,
    var userNickname: String?,
    var reward: RewardResp?,
    var missions: List<MissionResp>?,
){
    constructor(model: Coupon, nickname: String?, reward: Reward?): this(
        useDate = model.useDate?.let { convertToString(it) },
        status = model.status,
        couponId = model.couponId,
        expireDate = model.expireDate?.let { convertToString(it) },
        marketId = model.marketId,
        userNickname = nickname,
        missions = listOf(),
        reward = reward?.let { RewardResp(it) },
    )
}