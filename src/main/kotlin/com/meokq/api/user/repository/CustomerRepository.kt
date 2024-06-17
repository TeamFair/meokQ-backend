package com.meokq.api.user.repository

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, String> {
    fun findByEmail(email : String) : Customer?
    fun existsByNickname(nickname : String) : Boolean
    fun existsByEmail(email: String) : Boolean

    fun countByStatus(status: UserStatus) : Int
}