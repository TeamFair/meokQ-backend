package com.meokq.api.application.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_mission")
class TbMission : BaseModel() {
    @Id
    var sequence : Int? = null
    @Id
    var questId : String? = null

    var count : Int? = null
    var target : String? = null
    var content : String? = null
}