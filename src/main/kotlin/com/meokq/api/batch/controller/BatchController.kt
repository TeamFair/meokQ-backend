package com.meokq.api.batch.controller

import com.meokq.api.batch.service.StepService
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import java.time.LocalDate

class BatchController(
    private val jobRepository: JobRepository,
    private val stepService: StepService
    ) {


    @Bean
    @JobScope
    fun updateCustomerStatusJob(): Job {
        return JobBuilder("updateCustomerStatusStep",jobRepository)
            .start(stepService.updateCustomerStatusStep())
            .build()
    }



}
