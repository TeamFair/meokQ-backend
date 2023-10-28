package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_market")
class TbMarket : BaseModel() {
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