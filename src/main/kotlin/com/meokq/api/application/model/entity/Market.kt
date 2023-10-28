package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_market")
class Market : BaseModel() {
    @Id
    var marketId : String? = null

    var name : String? = null
    var address : String? = null
    var district : String? = null
    var content : String? = null
    var phone : String? = null
    var ticketCount : Int = 0
    var presidentId : String? = null
}