package com.meokq.api.batch.job

import com.meokq.api.batch.step.StepService
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchJob(
    private val jobRepository: JobRepository,
    private val stepService: StepService
    ){
    @Bean
    fun updateCustomerStatusJob(): Job {
        return JobBuilder("updateCustomerStatusJob",jobRepository)
            .start(stepService.updateCustomerStatusStep())
            .build()
    }



}
