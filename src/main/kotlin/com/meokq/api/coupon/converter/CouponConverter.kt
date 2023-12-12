package com.meokq.api.coupon.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.DateTimeConverter
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.request.CouponReq
import com.meokq.api.coupon.response.CouponResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CouponConverter : BaseConverter<CouponReq, CouponResp, Coupon> {

    @Autowired
    lateinit var dateTimeConverter: DateTimeConverter

    override fun modelToResponse(model: Coupon): CouponResp {
        return CouponResp(
            useDate = model.useDate?.let { dateTimeConverter.convertToString(it) },
            couponStatus = model.couponStatus,
            couponId = model.couponId,
            expireDate = model.expireDate?.let { dateTimeConverter.convertToString(it) },
            marketId = model.targetMarketId,
            // TODO : FIll nickname
            userNickname = null,
        )
    }

    override fun requestToModel(request: CouponReq): Coupon {
        return Coupon(
            challengeId = request.challengeId,
            targetMarketId = request.targetMarketId,
            targetUserId = request.targetUserId,
        )
    }
}