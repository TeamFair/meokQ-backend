package com.meokq.api.user.response

import com.meokq.api.user.enums.UserStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Customer-Response")
data class CustomerResp(
    @Schema(name = "상태값")
    val status : UserStatus,

    @Schema(name = "Customer Id")
    val customerId : String?
)
