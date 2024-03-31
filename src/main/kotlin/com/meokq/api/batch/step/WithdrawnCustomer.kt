package com.meokq.api.batch.step

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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

@EnableBatchProcessing
@Configuration
class WithdrawnCustomer(
    val entityManagerFactory: EntityManagerFactory,
    val transactionManager: PlatformTransactionManager,
    val jobRepository: JobRepository,
    val dataSource: DataSource
) : StepService<Customer> {

    val JOB_NAME = "WithdrawnCustomer"
    val CHUNK_SIZE : Int = 1000

    override fun step(): Step {
        return StepBuilder(JOB_NAME,jobRepository)
                .chunk<Customer, Customer>(CHUNK_SIZE,transactionManager)
                .reader(reader(null))
                .writer(bulkWriter())
                .startLimit(2)
                .build()
    }

    @StepScope
    override fun reader(@Value("#{jobParameters[date]}") date :String?): JpaPagingItemReader<Customer> {
        val cutOffDate = LocalDate.parse(date).minusDays(90)
        return JpaPagingItemReaderBuilder<Customer>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT c FROM tb_customer c WHERE c.status = 'DORMANT' AND c.withdrawnAt <= :cutoffDate")
            .parameterValues(mapOf("cutoffDate" to cutOffDate))
            .saveState(false)
            .build()
    }

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
