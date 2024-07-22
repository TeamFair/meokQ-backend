package com.meokq.api.batch.step

import com.meokq.api.file.model.Image
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
import java.sql.SQLException
import javax.sql.DataSource

@EnableBatchProcessing
@Configuration
class DeletedImage(
    private val entityManagerFactory: EntityManagerFactory,
    private val transactionManager: PlatformTransactionManager,
    private val jobRepository: JobRepository,
    private val dataSource: DataSource,
) : StepService<Image> {

    companion object {
        const val JOB_NAME = "deletedImage"
        const val CHUNK_SIZE: Int = 1000
    }

    @Bean(JOB_NAME + "_step")
    override fun step(): Step {
        return StepBuilder(JOB_NAME, jobRepository)
            .chunk<Image, Image>(CHUNK_SIZE, transactionManager)
            .reader(reader(null))
            .writer(bulkWriter())
            .startLimit(2)
            .build()
    }

    @StepScope
    @Bean(name = [JOB_NAME + "_reader"])
    override fun reader(@Value("#{jobParameters[date]}") date: String?): JpaPagingItemReader<Image> {
        return JpaPagingItemReaderBuilder<Image>()
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT i FROM tb_Image i WHERE i.is_deleted = true")
            .saveState(false)
            .build()
    }

    @Bean(name = [JOB_NAME + "_writer"])
    override fun bulkWriter(): ItemWriter<Image> {
        return ItemWriter<Image> { items ->
            val sql = "DELETE FROM tb_Image WHERE file_id = ?"
            val con = dataSource.connection ?: throw SQLException("Connection is null")
            con.autoCommit = false
            val pstmt = con.prepareStatement(sql)
            try {
                items.chunked(CHUNK_SIZE).forEach { chunks ->
                    for (chunk in chunks) {
                        pstmt.setString(1, chunk.fileId)
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