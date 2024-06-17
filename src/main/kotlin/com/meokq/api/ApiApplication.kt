package com.meokq.api

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAspectJAutoProxy
@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
class ApiApplication
fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}