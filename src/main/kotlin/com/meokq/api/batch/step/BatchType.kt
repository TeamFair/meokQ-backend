package com.meokq.api.batch.step

enum class BatchType(val jobName: String){
    EXPIRED_COUPON("expiredCouponStep"),
    EXPIRED_QUEST("expiredQuestStep"),
    WITHDRAWN_CUSTOMER("withdrawnCustomerStep"),
    DELETED_IMAGE("deletedImageStep"),

    ;

}