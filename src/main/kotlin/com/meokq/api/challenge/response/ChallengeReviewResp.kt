package com.meokq.api.challenge.response

import com.meokq.api.coupon.response.CouponResp

class ChallengeReviewResp(
    val challengeId: String?,
    val coupons: List<CouponResp> = listOf(),
)