package com.meokq.api.application.model

import com.meokq.api.application.enums.CouponStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
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
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)