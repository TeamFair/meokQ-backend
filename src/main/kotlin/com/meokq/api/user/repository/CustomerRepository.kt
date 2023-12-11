package com.meokq.api.user.repository

import com.meokq.api.user.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, String> {
    fun findByEmail(email : String) : Customer?
}