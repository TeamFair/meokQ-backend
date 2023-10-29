package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_reward")
class TbReward : BaseModel() {
    @Id
    var sequence : Int? = null
    @Id
    var questId : String? = null

    var count : Int? = null
    var discountRate : String? = null
    var content : String? = null
    var target : String? = null
}