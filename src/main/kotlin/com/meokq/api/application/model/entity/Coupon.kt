package com.meokq.api.application.model.entity

import com.meokq.api.application.enums.CouponStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity(name = "tb_coupon")
class Coupon {
    @Id
    var couponId : String? = null
    var status : CouponStatus? = null

    var customerId : String? = null
    var rewardSequence : Int? = null
    var questId : String? = null
    var useDate : LocalDateTime? = null
    var expiryDate : LocalDateTime? = null
}