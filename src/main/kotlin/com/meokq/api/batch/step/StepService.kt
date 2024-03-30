package com.meokq.api.batch.step

import org.springframework.batch.core.Step

interface StepService {
    fun updateCustomerStatusStep(): Step
}