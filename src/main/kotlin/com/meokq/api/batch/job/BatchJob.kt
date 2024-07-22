package com.meokq.api.batch.job

import com.meokq.api.batch.step.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val withdrawnCustomer: WithdrawnCustomer,
    val expiredCoupon: ExpiredCoupon,
    val expiredQuest: ExpiredQuest,
    val deletedImage: DeletedImage,
) {
    fun withdrawnCustomerJob(): Job {
        return JobBuilder(BatchType.WITHDRAWN_CUSTOMER.jobName, jobRepository)
            .start(withdrawnCustomer.step())
            .build()
    }
    fun expiredCouponJob(): Job {
        return JobBuilder(BatchType.EXPIRED_COUPON.jobName, jobRepository)
            .start(expiredCoupon.step())
            .build()
    }

    fun expiredQuestJob(): Job {
        return JobBuilder(BatchType.EXPIRED_QUEST.jobName, jobRepository)
            .start(expiredQuest.step())
            .build()
    }

    fun deletedImageJob(): Job {
        return JobBuilder(BatchType.DELETED_IMAGE.jobName, jobRepository)
            .start(deletedImage.step())
            .build()
    }



}
