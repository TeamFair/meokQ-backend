package com.meokq.api.application.model

import com.meokq.api.application.enums.UserType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "tb_notice")
@SequenceGenerator(name = "notice_seq_gen", sequenceName = "notice_sequence", allocationSize = 1)
data class Notice(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_seq_gen")
    var noticeId : Long? = null,
    var title : String? = null,
    var content : String? = null,
    var target : UserType? = null,

    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
)