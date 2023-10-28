package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_management")
class TbManagement : BaseModel() {
    @Id
    var managerId : String? = null
    @Id
    var marketId : String? = null
}