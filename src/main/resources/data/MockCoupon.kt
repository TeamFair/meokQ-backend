package data

import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import data.MockMarket.MK20000004
import data.MockMarket.MK20000005
import data.MockReward.RW20000004
import data.MockReward.RW20000005
import data.MockReward.RW20000006
import jdk.jfr.Description
import java.time.LocalDateTime

object MockCoupon {
    @Description("발행된 쿠폰")
    val CP20000001 = Coupon(
        couponId = "CP20000001",
        status = CouponStatus.ISSUED,
        useDate = null,
        rewardId = RW20000004.rewardId,
        marketId = MK20000004.marketId,
    )

    @Description("사용된 쿠폰")
    val CP20000002 = Coupon(
        couponId = "CP20000002",
        status = CouponStatus.USED,
        useDate = LocalDateTime.now(),
        rewardId = RW20000005.rewardId,
        marketId = MK20000004.marketId,
    )

    @Description("만료된 쿠폰")
    val CP20000003 = Coupon(
        couponId = "CP20000003",
        status = CouponStatus.EXPIRED,
        useDate = null,
        rewardId = RW20000006.rewardId,
        marketId = MK20000005.marketId,
    )
}