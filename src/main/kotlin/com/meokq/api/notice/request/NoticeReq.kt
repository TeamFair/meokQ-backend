package com.meokq.api.notice.request

import com.meokq.api.notice.enums.NoticeTarget
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

@Schema(name = "Notice-Request")
data class NoticeReq(
    @field:NotEmpty(message = "제목은 필수입니다.")
    @Schema(description = "공지 제목")
    var title : String,

    @field:NotEmpty(message = "내용은 필수입니다.")
    @Schema(description = "공지 본문")
    var content : String,

    @Schema(description = "공지를 노출할 대상")
    var target : NoticeTarget
)