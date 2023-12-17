package com.meokq.api.auth.request

import com.meokq.api.auth.enums.UserType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Request")
data class AuthReq(
    @Schema(description = "User type (BOSS, CUSTOMER, ADMIN, UNKNOWN)")
    val userType: UserType = UserType.UNKNOWN,

    @Schema(description = "User ID")
    val userId: String? = null,
)
