package com.meokq.api.coupon.response

import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.quest.response.QuestResp

data class CouponResp (
    var quest : QuestResp? = null,
    val useDate : String?,
    val couponStatus: CouponStatus?,
    val couponId : String?,
    val expireDate : String?,
    val marketId : String?,
    val userNickname : String?,
)