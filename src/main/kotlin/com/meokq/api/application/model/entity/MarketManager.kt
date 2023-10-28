package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_market_manager")
class MarketManager : BaseUserModel() {
    @Id
    var managerId : String? = null
    var position : String? = null
}