package com.meokq.api.batch.job

import com.meokq.api.batch.step.*
import org.springframework.batch.core.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    val jobRepository: JobRepository,
    val expiredCoupon: ExpiredCoupon,
    val expiredQuest: ExpiredQuest,
    val withdrawnCustomer: WithdrawnCustomer,
    val deletedImage: DeletedImage
) {
    fun withdrawnCustomerJob(): Job {
        return JobBuilder(BatchType.WITHDRAWN_CUSTOMER.name,jobRepository)
            .start(withdrawnCustomer.step())
            .build()
    }
    fun expiredCouponJob(): Job {
        return JobBuilder(BatchType.EXPIRED_COUPON.name,jobRepository)
            .start(expiredCoupon.step())
            .build()
    }
    @Deprecated("240730 퀘스트 삭제 정책 변경에 따른 미사용")
    fun expiredQuestJob(): Job {
        return JobBuilder(BatchType.EXPIRED_QUEST.name,jobRepository)
            .start(expiredQuest.step())
            .build()
    }
    fun deletedImageJob(): Job {
        return JobBuilder(BatchType.DELETED_IMAGE.name,jobRepository)
            .start(deletedImage.step())
            .build()
    }
}
