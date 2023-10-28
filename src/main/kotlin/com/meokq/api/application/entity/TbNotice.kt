package com.meokq.api.application.entity

import com.meokq.api.application.enums.UserType
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_notice")
class TbNotice : BaseModel() {
    @Id
    var noticeId : String? = null

    var title : String? = null
    var content : String? = null
    var target : UserType? = null
}