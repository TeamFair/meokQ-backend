package com.meokq.api.batch.service

import com.meokq.api.user.service.CustomerService
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder

class batchService(
    val customerService: CustomerService,
    val processBuilder: ProcessBuilder,
    val stepBuilder: StepBuilder,
    val jobBuilder: JobBuilder

)

{


    fun batchService() {

    }
}