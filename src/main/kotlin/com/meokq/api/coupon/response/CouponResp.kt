package com.meokq.api.coupon.response

import com.meokq.api.coupon.model.Coupon

class CouponResp(
    val couponId: String?,
    val rewardId: String?,
){
    constructor(model: Coupon): this(
        couponId = model.couponId,
        rewardId = model.rewardId,
    )
}