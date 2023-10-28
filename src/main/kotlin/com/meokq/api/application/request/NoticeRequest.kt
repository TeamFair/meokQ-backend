package com.meokq.api.application.request

import com.meokq.api.application.enums.UserType

data class NoticeRequest(
    var title : String?,
    var content : String?,
    var target : UserType?
)