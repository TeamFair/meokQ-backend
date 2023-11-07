package com.meokq.api.application.model

import com.meokq.api.application.enums.UserType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity
@Table(name = "tb_notice")
data class Notice(
    @Id
    @UuidGenerator
    var noticeId : String? = null,
    var title : String? = null,
    var content : String? = null,
    var target : UserType? = null,

    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
)