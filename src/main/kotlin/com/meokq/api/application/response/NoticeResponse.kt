package com.meokq.api.application.response

import com.meokq.api.application.enums.UserType

data class NoticeResponse(
    val noticeId : String?,
    val title : String?,
    val content : String?,
    val createDate : String?,
    val target : UserType?
)