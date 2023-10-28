package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_admin")
class Admin : BaseUserModel() {
    @Id
    var adminId : String? = null
}