package com.meokq.api.batch.step

import com.meokq.api.batch.common.StepListener
import com.meokq.api.user.model.Customer
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Step
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
import java.sql.SQLException
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
class WithdrawCustomer(
    private val entityManagerFactory: EntityManagerFactory,
    private val transactionManager: PlatformTransactionManager,
    private val jobRepository: JobRepository,
    private val dataSource: DataSource,
    private val stepListener: StepListener
) : StepService<Customer> {
    companion object {
        const val JOB_NAME = "withdrawCustomer"
        const val CHUNK_SIZE : Int = 1000
    }

    @Bean(name = [JOB_NAME+"_step"])
    override fun step(): Step {
        return StepBuilder(JOB_NAME,jobRepository)
                .chunk<Customer, Customer>(CHUNK_SIZE,transactionManager)
                .reader(reader(null))
                .writer(bulkWriter())
                .listener(stepListener.terminateStepIfReadCountIsZeroListener())
                .startLimit(2)
                .build()
    }

    @StepScope
    @Bean(name = [JOB_NAME+"_reader"])
    override fun reader(@Value("#{jobParameters[date]}") date :String?): JpaPagingItemReader<Customer> {
        val cutOffDate = LocalDateTime.parse(date).minusDays(90)
        return JpaPagingItemReaderBuilder<Customer>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT c FROM tb_customer c WHERE c.status = 'DORMANT' AND c.withdrawAt <= :cutOffDate")
            .parameterValues(mapOf("cutOffDate" to cutOffDate))
            .saveState(false)
            .build()
    }

    @Bean(name =[JOB_NAME+"_writer"])
    override fun bulkWriter(): ItemWriter<Customer> {
        return ItemWriter<Customer> { items ->
            val sql = "DELETE FROM tb_customer WHERE customer_id = ?;"
            val con = dataSource.connection ?: throw SQLException("Connection is null")
                con.autoCommit = false
            val pstmt = con.prepareStatement(sql)
            try {
                items.chunked(CHUNK_SIZE).forEach { chunks ->
                    for (chunk in chunks) {
                        pstmt.setString(1, chunk.customerId)
                        pstmt.addBatch()
                    }
                    pstmt.executeBatch()
                    con.commit()
                }

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
