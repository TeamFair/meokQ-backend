package com.meokq.api.application.model

import com.meokq.api.application.enums.UserType
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_notice")
data class Notice(
    @Id
    @UuidGenerator
    var noticeId : String? = null,
    var title : String? = null,
    var content : String? = null,
    var target : UserType? = null,
) : BaseModel()