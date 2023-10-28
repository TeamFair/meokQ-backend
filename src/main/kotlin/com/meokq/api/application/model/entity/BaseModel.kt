package com.meokq.api.application.model.entity

import com.meokq.api.application.enums.TypeYN
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
open class BaseModel {
    var deleteYn : TypeYN = TypeYN.N
    var createDate : LocalDateTime? = null
    var updateDate : LocalDateTime? = null
}