package com.meokq.api.coupon

import com.meokq.api.coupon.CouponStatus
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity
class Coupon(
    @Id
    @UuidGenerator
    var couponId : String? = null,
    var couponStatus: CouponStatus = CouponStatus.ISSUED,
    var useDate : LocalDateTime? = null,
    var expireDate : LocalDateTime? = null,
    var targetUserId : String? = null,
    var targetMarketId : String? = null,
    var rewardId : String? = null,
) : BaseModel()