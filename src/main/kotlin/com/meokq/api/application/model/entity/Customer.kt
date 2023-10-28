package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_customer")
class Customer : BaseUserModel() {
    @Id
    var customerId : String? = null
}