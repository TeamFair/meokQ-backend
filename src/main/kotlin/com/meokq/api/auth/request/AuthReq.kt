package com.meokq.api.auth.request

import com.meokq.api.auth.enums.UserType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Request")
data class AuthReq(
    @Schema(description = "User type (BOSS, CUSTOMER, ADMIN)")
    val userType: UserType,

    @Schema(description = "User ID")
    val userId: String,
)
