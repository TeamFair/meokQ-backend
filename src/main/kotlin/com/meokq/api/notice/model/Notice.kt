package com.meokq.api.notice.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.notice.enums.NoticeTarget
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
    var target : NoticeTarget? = null,
) : BaseModel()