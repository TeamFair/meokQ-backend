package com.meokq.api.coupon.response

import com.meokq.api.core.converter.DateTimeConverterV2.convertToString
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.response.RewardResp

data class CouponResp(
    val useDate: String?,
    val status: CouponStatus?,
    val couponId: String?,
    val expireDate: String?,
    val marketId: String?,
    var userNickname: String?,
    var reward: RewardResp?,
    var missionTitles: List<String>?,
){
    constructor(model: Coupon, nickname: String?, reward: Reward?, missions: List<Mission>? = listOf()): this(
        useDate = model.useDate?.let { convertToString(it) },
        status = model.status,
        couponId = model.couponId,
        expireDate = model.expireDate?.let { convertToString(it) },
        marketId = model.marketId,
        userNickname = nickname,
        missionTitles = missions?.stream()?.map{ MissionType.getTitle(it)}?.toList(),
        reward = reward?.let { RewardResp(it) },
    )
}