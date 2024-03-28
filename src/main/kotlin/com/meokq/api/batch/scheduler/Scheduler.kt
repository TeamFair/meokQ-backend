package com.meokq.api.batch.scheduler

import com.meokq.api.batch.controller.BatchController
import com.meokq.api.user.enums.UserStatus
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import java.sql.Date
import java.time.LocalDate


class Scheduler(val batchController: BatchController,val customerRepository: C,val jobLauncher : JobLauncher){
    @Scheduled(cron="0 0 24 * * ?")
    fun run() {
        val CHUNK_SIZE = 1000
        val dataSize = customerRepository.countByStatus(UserStatus.DORMANT)
        val iteratorSize = if (dataSize > CHUNK_SIZE) dataSize / CHUNK_SIZE else 1

        val jobExecution = jobLauncher.run(batchController.updateCustomerStatusJob(),
            JobParametersBuilder().addDate("date",Date.valueOf(LocalDate.now()))
                .toJobParameters()
            )

    }
}