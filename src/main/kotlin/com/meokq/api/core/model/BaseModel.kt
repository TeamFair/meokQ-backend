package com.meokq.api.core.model

import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
open class BaseModel {
    @CreationTimestamp
    var createDate : LocalDateTime? = null
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
    /**@CreatedBy
    var createdBy : String? = null
    var updateBy : String? = null**/
}