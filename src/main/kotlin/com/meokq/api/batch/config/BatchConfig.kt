package com.meokq.api.batch.config

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.annotation.Configuration


@Configuration
class BatchConfig(val jobRepository: JobRepository) : DefaultBatchConfiguration(){



}