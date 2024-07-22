package com.meokq.api.batch.job

import com.meokq.api.batch.step.AdaptStep
import com.meokq.api.batch.step.BatchType
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val adaptStep: AdaptStep
) {
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

    fun expiredQuestJob(): Job {
        return JobBuilder(BatchType.EXPIRED_QUEST.toString(),jobRepository)
            .start(adaptStep.getStep(BatchType.EXPIRED_QUEST))
            .build()
    }

    fun deletedImageJob(): Job {
        return JobBuilder(BatchType.DELETED_IMAGE.toString(),jobRepository)
            .start(adaptStep.getStep(BatchType.DELETED_IMAGE))
            .build()
    }
}
