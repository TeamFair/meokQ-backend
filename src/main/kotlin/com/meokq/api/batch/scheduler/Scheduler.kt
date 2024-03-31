package com.meokq.api.batch.scheduler

import com.meokq.api.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
class Scheduler(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher) {
    @Scheduled(cron = "0 0 0 * * ?")
        fun run(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDateTime.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.withdrawnCustomerJob(), jobParameter)
        jobLauncher.run(batchJob.expiredCouponJob(), jobParameter)
    }

    @GetMapping("/api/open/test")
    fun testRun(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDateTime.now().toString()).toJobParameters()
    }
}