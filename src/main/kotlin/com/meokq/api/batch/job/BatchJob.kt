package com.meokq.api.batch.job

import com.meokq.api.batch.step.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val adaptStep: AdaptStep ) {
    fun withdrawnCustomerJob(): Job {
        return JobBuilder(BatchType.WITHDRAWN_CUSTOMER.toString(),jobRepository)
            .start(adaptStep.getStep(BatchType.WITHDRAWN_CUSTOMER))
            .build()
    }
    fun expiredCouponJob(): Job {
        return JobBuilder(BatchType.EXPIRED_COUPON.toString(),jobRepository)
            .start(adaptStep.getStep(BatchType.EXPIRED_COUPON))
            .build()
    }



}
