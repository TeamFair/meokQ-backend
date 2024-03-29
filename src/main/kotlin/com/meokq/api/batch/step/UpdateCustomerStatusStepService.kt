package com.meokq.api.batch.step

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.sql.Date
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class UpdateCustomerStatusStepService(
    private val entityManagerFactory: EntityManagerFactory,
    private val transactionManager: PlatformTransactionManager,
    private val jobRepository: JobRepository,
    private val dataSource: DataSource,
) : StepService {
        val CHUNK_SIZE : Int = 1000

    @StepScope
    @Bean
    override fun updateCustomerStatusStep(): Step {
        return StepBuilder("updateCustomerStatusStep",jobRepository)
                .chunk<Customer, Customer>(CHUNK_SIZE,transactionManager)
                .reader(reader(null))
                .writer(bulkWriter())
                .build()
    }

    @StepScope
    @Bean
    fun reader(@Value("#{jobParameters[date]}") date :String?): JpaPagingItemReader<Customer> {
        println(date)
        val cutoffDate = LocalDate.parse(date).minusDays(1)
        return JpaPagingItemReaderBuilder<Customer>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT * FROM tb_customer WHERE status = 'DORMANT' AND withdrawnAt <= :cutoffDate")
            .parameterValues(mapOf("cutoffDate" to cutoffDate))
            .build()
    }

    @Bean
    fun bulkWriter(): ItemWriter<Customer> {
        return ItemWriter<Customer> { customers ->
            checkNotNull(customers)
            customers.chunked(CHUNK_SIZE).forEach { chunk ->
                val con = dataSource.connection ?: throw SQLException("Connection is null")
                val sql = "INSERT INTO tb_customer (customerId, status, email, nickname, channel, withdrawnAt) VALUES (?, ?, ?, ?, ?, ?);"
                val pstmt = con.prepareStatement(sql)

                try {
                    con.autoCommit = false

                    for (customer in chunk) {
                        customer.status = UserStatus.WITHDRAWN

                        pstmt.setString(1, customer.customerId)
                        pstmt.setString(2, customer.status.name)
                        pstmt.setString(3, customer.email)
                        pstmt.setString(4, customer.nickname)
                        pstmt.setString(5, customer.channel?.name)
                        pstmt.setDate(6, Date.valueOf(customer.withdrawnAt))

                        pstmt.addBatch()
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
                    pstmt.close()
                    con.close()
                }
            }
        }
    }


}
