package com.meokq.api.coupon.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon

interface CouponRepository : BaseRepository<Coupon, String> {
    fun countByUserIdAndStatus(userId : String, status: CouponStatus) : Long
}