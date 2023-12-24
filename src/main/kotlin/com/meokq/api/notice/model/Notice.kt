package com.meokq.api.notice.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.request.NoticeReq
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_notice")
data class Notice(
    @Id
    @UuidGenerator
    var noticeId : String? = null,
    var title : String? = null,
    var content : String? = null,
    @Enumerated(EnumType.STRING)
    var target : NoticeTarget? = null,
) : BaseModel(){
    constructor(req: NoticeReq) : this(
        title = req.title,
        content = req.content,
        target = req.target
    )
}