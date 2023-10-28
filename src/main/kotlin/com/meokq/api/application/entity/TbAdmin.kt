package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_admin")
class TbAdmin : BaseUserModel() {
    @Id
    var adminId : String? = null
}