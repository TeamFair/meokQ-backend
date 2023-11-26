package com.meokq.api.notice.request

import com.meokq.api.notice.enums.NoticeTarget
import org.springframework.data.domain.Pageable

data class NoticeSearchDto(
    var target : NoticeTarget,
    var pageable: Pageable
)