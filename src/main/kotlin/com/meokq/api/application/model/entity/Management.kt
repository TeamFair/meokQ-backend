package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_management")
class Management : BaseModel() {
    @Id
    var managerId : String? = null
    @Id
    var marketId : String? = null
}