package com.meokq.api.batch.scheduler

import com.meokq.api.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
class Scheduler(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher) {

    //매일 0시에 실행
    @Scheduled(cron = "0 0 0 * * ?")
    fun run(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDate.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.withdrawnCustomerJob(), jobParameter)
    }

    @GetMapping("/api/open/test")
    fun testRun(){
        val jobParameter = JobParametersBuilder().addString("date", LocalDateTime.now().toString()).toJobParameters()
        jobLauncher.run(batchJob.expiredCouponJob(), jobParameter)
    }
}
