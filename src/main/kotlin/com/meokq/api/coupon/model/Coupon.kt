package com.meokq.api.coupon.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.coupon.enums.CouponStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_coupon")
class Coupon(
    @Id
    @UuidGenerator
    var couponId : String? = null,
    var couponStatus: CouponStatus = CouponStatus.ISSUED,
    var useDate : LocalDateTime? = null,
    // TODO : 초기값은 정책 결정 전까지 max로 지정
    var expireDate : LocalDateTime? = LocalDateTime.MAX,
    var challengeId : String? = null,
    var targetUserId : String? = null, // Customer model 의 id 와 연결되는 외부 키
    var targetMarketId : String? = null, // Market model 의 id 와 연결되는 외래 키
) : BaseModel()