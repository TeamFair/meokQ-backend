package com.meokq.api.notice.response

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.model.Notice
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Notice-Response")
data class NoticeResp(
    @Schema(description = "공지사항 ID")
    val noticeId : String?,

    @Schema(description = "공지사항 제목")
    val title : String?,

    @Schema(description = "공지사항 본문")
    val content : String?,

    @Schema(description = "공지사항 생성일시")
    val createDate : String?,

    @Schema(description = "공지 대상")
    val target : NoticeTarget?
){
    constructor(model : Notice) : this(
        noticeId = model.noticeId,
        title = model.title,
        content = model.content,
        createDate = model.createDate?.let { DateTimeConverterV2.convertToString(it) },
        target = model.target
    )
}