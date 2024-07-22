package com.meokq.api.batch.step

enum class BatchType(val jobName: String) {
    EXPIRED_COUPON(jobName = "expiredQuest"),
    EXPIRED_QUEST(jobName = "expiredCoupon"),
    WITHDRAWN_CUSTOMER(jobName = "withdrawnCustomer"),
    DELETED_IMAGE(jobName = "deletedImage"),
    ;

}