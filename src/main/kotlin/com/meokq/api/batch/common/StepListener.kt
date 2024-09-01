package com.meokq.api.batch.common

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class StepListener {
    @Bean
    fun terminateStepIfReadCountIsZeroListener(): StepExecutionListener {
        return object : StepExecutionListener {

            override fun afterStep(stepExecution: StepExecution): ExitStatus? {
                if (stepExecution.readCount == 0L) {
                    return ExitStatus.COMPLETED
                }
                return null
            }
        }
    }

}