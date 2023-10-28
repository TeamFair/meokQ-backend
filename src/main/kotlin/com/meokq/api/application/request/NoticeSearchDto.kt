package com.meokq.api.application.request

import com.meokq.api.application.enums.UserType
import org.springframework.data.domain.Pageable

data class NoticeSearchDto(
    var target : UserType,
    var pageable: Pageable
)