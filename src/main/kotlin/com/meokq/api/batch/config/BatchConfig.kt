package com.meokq.api.batch.config

import org.springframework.batch.core.configuration.BatchConfigurationException
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


@Configuration
class BatchConfig(val jobRepository: JobRepository) : DefaultBatchConfiguration(){
    @Throws(Exception::class)
    fun customJobRepository(): JobRepository {
        val factory = JobRepositoryFactoryBean()
        factory.setDataSource(dataSource)
        factory.transactionManager = transactionManager
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE")
        factory.setTablePrefix("BATCH_")
        factory.setMaxVarCharLength(1000)
        return factory.`object`
    }

    @Bean
    fun asyncJobLauncher(): JobLauncher {
        val taskExecutorJobLauncher = TaskExecutorJobLauncher()
        taskExecutorJobLauncher.setJobRepository(jobRepository)
        taskExecutorJobLauncher.setTaskExecutor(getTaskExecutor())
        try {
            taskExecutorJobLauncher.afterPropertiesSet()
            return taskExecutorJobLauncher
        } catch (e: Exception) {
            throw BatchConfigurationException("Unable to configure the default job launcher", e)
        }
    }

    @Bean
    override fun getTaskExecutor(): AsyncTaskExecutor {
        //비동기 프로그램으로 변경
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 25
        executor.initialize()
        return executor
    }
}