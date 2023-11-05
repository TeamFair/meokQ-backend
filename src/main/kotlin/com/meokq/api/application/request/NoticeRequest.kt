package com.meokq.api.application.request

import com.meokq.api.application.enums.UserType
import jakarta.validation.constraints.NotEmpty

data class NoticeRequest(
    @field:NotEmpty(message = "제목은 필수입니다.")
    var title : String,
    @field:NotEmpty(message = "내용은 필수입니다.")
    var content : String,
    @field:NotEmpty(message = "공지대상은 필수입니다.")
    var target : UserType
)