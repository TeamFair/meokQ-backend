package com.meokq.api.coupon.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import org.springframework.data.jpa.repository.Query

interface CouponRepository : BaseRepository<Coupon, String> {
    fun countByUserIdAndStatus(userId : String, status: CouponStatus) : Long

    @Query("select c from tb_coupon c where c.challengeId=:challengeId")
    fun findAllByChallengeId(challengeId: String) : List<Coupon>
}