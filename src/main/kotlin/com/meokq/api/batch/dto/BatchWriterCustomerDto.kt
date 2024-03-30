package com.meokq.api.batch.dto

import com.meokq.api.user.enums.UserStatus
import java.time.LocalDate

open class BatchWriterCustomerDto
{
    lateinit var customerId : String
    lateinit var status : UserStatus
    lateinit var withdrawnAt : LocalDate
}
