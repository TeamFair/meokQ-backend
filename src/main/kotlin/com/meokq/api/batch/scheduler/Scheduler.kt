package com.meokq.api.batch.scheduler

import com.meokq.api.batch.job.BatchJob
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Date
import java.time.LocalDate

@RestController
@RequestMapping("/api")
class Scheduler(
    private val batchJob: BatchJob,
    private val jobLauncher : JobLauncher){

    @Scheduled(cron="0 0 0 * * ?")
    @PostMapping(value = ["/open/test"])
    fun run() : String {
        try {
            val jobParameter = JobParametersBuilder().addString("date",LocalDate.now().toString()).toJobParameters()
            jobLauncher.run(batchJob.updateCustomerStatusJob(),jobParameter)
            return "Done"
        }catch(e : Exception){
            return e.message.toString()
        }
    }

 }
