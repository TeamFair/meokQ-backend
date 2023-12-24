package com.meokq.api.coupon.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.coupon.enums.CouponStatus
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.time.LocalDateTime

@Entity(name = "tb_coupon")
class Coupon(
    @Id
    @GenericGenerator(
        name = "SEQ_GEN_COUPON",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "COUPON_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_COUPON")
    var couponId : String? = null,
    @Enumerated(EnumType.STRING)
    var status: CouponStatus = CouponStatus.ISSUED,
    var useDate : LocalDateTime? = null,
    // TODO : 초기값은 정책 결정 전까지 max로 지정
    var expireDate : LocalDateTime? = LocalDateTime.of(2999, 12, 31, 12, 59),
    var challengeId : String? = null,
    var questId : String? = null,
    var rewardId : String? = null,
    var marketId : String? = null,
    var userId : String? = null, // Customer model 의 id 와 연결되는 외부 키
) : BaseModel()