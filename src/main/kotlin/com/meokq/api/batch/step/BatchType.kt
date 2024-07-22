package com.meokq.api.batch.step

enum class BatchType {
    EXPIRED_COUPON,
    EXPIRED_QUEST,
    WITHDRAWN_CUSTOMER,
    DELETED_IMAGE,

    ;
    override fun toString(): String {
        return when(this){
            EXPIRED_QUEST-> "expiredQuest"
            EXPIRED_COUPON -> "expiredCoupon"
            WITHDRAWN_CUSTOMER -> "withdrawnCustomer"
            DELETED_IMAGE -> "deletedImage"
        }
    }
}