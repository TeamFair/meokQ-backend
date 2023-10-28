package com.meokq.api.application.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_mission")
class Mission : BaseModel() {
    @Id
    var sequence : Int? = null
    @Id
    var questId : String? = null

    var count : Int? = null
    var target : String? = null
    var content : String? = null
}