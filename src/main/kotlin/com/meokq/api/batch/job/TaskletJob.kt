package com.meokq.api.batch.job

import com.meokq.api.batch.config.BatchConfig
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate


@Configuration
class TaskletJob(
    val entityManagerFactory: EntityManagerFactory,
    val batchConfig: BatchConfig,
    val transactionManager: PlatformTransactionManager,
    val jobRepository: JobRepository
) {

    val chunkSize : Int = 10000

    @Bean
    @JobScope
    fun jobBatchBuilder(@Value("#{jobParameters='date'}") date:String): Job {
        return JobBuilder("updateCustomerStatusStep",batchConfig.customJobRepository())
            .start(updateCustomerStatusStep(date))
            .build()
    }

    @Bean
    @JobScope
    fun updateCustomerStatusJob(@Value("#{jobParameters='date'}") date:String): Job {
        return JobBuilder("updateCustomerStatusJob",batchConfig.customJobRepository())
            .start(updateCustomerStatusStep(date))
            .build()
    }

    @Bean
    @JobScope
    fun updateCustomerStatusStep(@Value("#{jobParameters='date'}") date:String):Step{
        return StepBuilder("updateCustomerStatusStep",jobRepository)
            .chunk<Customer, Customer>(chunkSize,transactionManager)
            .reader(reader(date))
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean
    @StepScope
    fun reader(@Value("#{jobParameters='date'}") date:String): JpaPagingItemReader<Customer> {
        val cutoffDate = LocalDate.parse(date).minusDays(90)
        return JpaPagingItemReaderBuilder<Customer>()
            .name("reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT * FROM tb_customer WHERE status = 'WITHDRAWN' AND withdrawnAt <= :cutoffDate")
            .parameterValues(mapOf("cutoffDate" to cutoffDate))
            .build()
    }

    @Bean
    @StepScope
    fun processor(): ItemProcessor<Customer, Customer> {
        return ItemProcessor<Customer, Customer> { customer ->
            customer.status = UserStatus.WITHDRAWN
            customer
        }
    }
    @Bean
    @StepScope
    fun writer(): ItemWriter<Customer> {
        return JpaItemWriterBuilder<Customer>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }


}
