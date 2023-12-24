package com.meokq.api.notice.request

import com.meokq.api.notice.enums.NoticeTarget
import io.swagger.v3.oas.annotations.media.Schema

data class NoticeSearchDto(
    @Schema(description = "공지사항을 노출할 타켓")
    var target : NoticeTarget
)