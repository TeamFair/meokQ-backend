package com.meokq.api.application.model

import com.meokq.api.application.enums.UserType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "tb_notice")
data class Notice(
    @Id
    @GenericGenerator(
        name = "notice_id_gen",
        strategy = "com.meokq.api.core.idGenerator.CustomIdGenerator",
        parameters = [
            Parameter(name = "sequenceName", value = "notice_sequence"),
            Parameter(name = "prefix", value = "NT"),
        ]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_id_gen")
    var noticeId : String? = null,
    var title : String? = null,
    var content : String? = null,
    var target : UserType? = null,

    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
)