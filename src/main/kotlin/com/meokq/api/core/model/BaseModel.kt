package com.meokq.api.core.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class BaseModel {
    @CreationTimestamp
    var createDate : LocalDateTime? = null
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null

    @CreatedBy
    var createdBy : String? = null
    @LastModifiedBy
    var updateBy : String? = null
}