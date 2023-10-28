package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_customer")
class TbCustomer : BaseUserModel() {
    @Id
    var customerId : String? = null
}