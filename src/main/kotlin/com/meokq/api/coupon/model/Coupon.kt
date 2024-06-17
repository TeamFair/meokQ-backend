package com.meokq.api.coupon.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.request.CouponSaveReq
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_coupon")
class Coupon(
    @Id
    /*@GenericGenerator(
        name = "SEQ_GEN_COUPON",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "COUPON_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_COUPON")*/
    @UuidGenerator
    var couponId : String? = null,
    @Enumerated(EnumType.STRING)
    var status: CouponStatus = CouponStatus.ISSUED,
    var useDate : LocalDateTime? = null,
    // TODO : 초기값은 정책 결정 전까지 max로 지정
    var expireDate : LocalDateTime? = LocalDateTime.of(2999, 12, 31, 12, 59),
    var challengeId : String? = null,
    var questId : String? = null,
    // TODO: reward를 항상 함께 조회하는 것을 고려
    var rewardId : String? = null,
    var marketId : String? = null,

    // TODO: user을 항상 함께 조회하는 것을 고려
    var userId : String? = null,
    //var userNickname : String? = null,

) : BaseModel(){

    constructor(req: CouponSaveReq): this(
        challengeId = req.challengeId,
        questId = req.questId,
        rewardId = req.rewardId,
        marketId = req.marketId,
        userId = req.userId,
    )
}