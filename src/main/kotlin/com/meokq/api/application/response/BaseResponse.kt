package com.meokq.api.application.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.meokq.api.application.enums.ErrorStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse(
    data : Any?,
    errorStatus : ErrorStatus
) {
    var data : Any? = data
    var status : String? = errorStatus.name
    var message : String? = errorStatus.message
}