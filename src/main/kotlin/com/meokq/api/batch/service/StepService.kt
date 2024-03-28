package com.meokq.api.batch.service

import org.springframework.batch.core.Step
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate

interface StepService {
    fun updateCustomerStatusStep(@Value("#{jobParameters='date'}") date : LocalDate): Step

}