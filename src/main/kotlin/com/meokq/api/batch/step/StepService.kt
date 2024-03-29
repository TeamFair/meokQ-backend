package com.meokq.api.batch.step

import org.springframework.batch.core.Step
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate

interface StepService {
    fun updateCustomerStatusStep(): Step
}