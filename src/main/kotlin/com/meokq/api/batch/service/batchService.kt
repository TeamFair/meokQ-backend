package com.meokq.api.batch.service

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
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

class batchService(
    private val entityManagerFactory: EntityManagerFactory,
    private val transactionManager: PlatformTransactionManager,
    private val jobRepository: JobRepository,
    private val dataSource: DataSource,
    ) {
    val chunkSize : Int = 1000
    val poolSize : Int = 10

    @Bean
    fun executor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = poolSize
        executor.maxPoolSize = poolSize
        executor.setThreadNamePrefix("batch-thread-")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }

    @Bean
    @JobScope
    private fun updateCustomerStatusJob(@Value("#{jobParameters='date'}") date:String): Job {
        return JobBuilder("updateCustomerStatusJob",jobRepository)
            .start(updateCustomerStatusStep(date))
            .build()
    }

    @Bean
    @StepScope
    private fun updateCustomerStatusStep(@Value("#{jobParameters='date'}") date:String): Step {
        return StepBuilder("updateCustomerStatusStep",jobRepository)
            .chunk<Customer, Customer>(chunkSize,transactionManager)
            .reader(reader(date))
            .writer(bulkWriter())
            .taskExecutor(executor())
            .build()
    }

    @Bean
    @StepScope
    private fun reader(@Value("#{jobParameters='date'}") date:String): JpaPagingItemReader<Customer> {
        val cutoffDate = LocalDate.parse(date).minusDays(90)
        return JpaPagingItemReaderBuilder<Customer>()
            .entityManagerFactory(entityManagerFactory)
            .pageSize(chunkSize)
            .queryString("SELECT * FROM tb_customer WHERE status = 'DORMANT' AND withdrawnAt <= :cutoffDate")
            .parameterValues(mapOf("cutoffDate" to cutoffDate))
            .build()
    }
    private fun bulkWriter(): ItemWriter<Customer> {
        return ItemWriter<Customer> { customers ->
            val con = dataSource.connection ?: throw SQLException("Connection is null")
            val sql = "INSERT INTO tb_customer (customerId, status, email, nickname, channel, withdrawnAt) VALUES (?, ?, ?, ?, ?, ?);"
            val pstmt = con.prepareStatement(sql)
            try {
                con.autoCommit = false

                var count = 0
                for (customer in customers) {
                    customer.status = UserStatus.WITHDRAWN
                    val stringConvert = customer.withdrawnAt.toString()

                    pstmt.setString(1, customer.customerId)
                    pstmt.setString(2, customer.status.name)
                    pstmt.setString(3, customer.email)
                    pstmt.setString(4, customer.nickname)
                    pstmt.setString(5, customer.channel?.name)
                    pstmt.setString(6, stringConvert)

                    pstmt.addBatch()

                    count++
                    if (count % chunkSize == 0) {
                        pstmt.executeBatch()
                        con.commit()
                    }
                }

                pstmt.executeBatch()
                con.commit()
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    con.rollback()
                } catch (sqlException: SQLException) {
                    sqlException.printStackTrace()
                }
            } finally {
                pstmt!!.close()
                con.close()
            }
        }
    }

    }
