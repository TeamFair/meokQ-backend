package com.meokq.api.coupon.response

import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.quest.response.MissionResp
import com.meokq.api.quest.response.RewardResp

data class CouponResp (
    val useDate : String?,
    val status: CouponStatus?,
    val couponId : String?,
    val expireDate : String?,
    val marketId : String?,
    var userNickname : String?,
    var reward: RewardResp?,
    var missions : List<MissionResp>?,
)