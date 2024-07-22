package com.meokq.api.batch.step

import org.springframework.batch.core.Step
import org.springframework.stereotype.Component

@Component
class AdaptStep(
    val withdrawnCustomer: WithdrawnCustomer,
    val expiredCoupon: ExpiredCoupon,
    val expiredQuest: ExpiredQuest
){
    fun getStep(batchType: BatchType): Step {
        when(batchType){
            BatchType.EXPIRED_QUEST -> return expiredQuest.step()
            BatchType.WITHDRAWN_CUSTOMER -> return withdrawnCustomer.step()
            BatchType.EXPIRED_COUPON -> return expiredCoupon.step()
            else -> throw IllegalArgumentException("Invalid step name")
        }
    }

}