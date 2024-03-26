package com.meokq.api.batch.scheduler

import com.meokq.api.batch.job.TaskletJob
import org.springframework.context.annotation.Configuration

@Configuration
class Scheduler (val taskletJob: TaskletJob){

    fun runJob() {
        taskletJob.jobBatchBuilder()
    }


}