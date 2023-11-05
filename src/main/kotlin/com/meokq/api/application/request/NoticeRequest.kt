package com.meokq.api.application.request

import com.meokq.api.application.enums.UserType
import org.jetbrains.annotations.NotNull

data class NoticeRequest(
    @NotNull
    var title : String?,
    @NotNull
    var content : String?,
    @NotNull
    var target : UserType?
)