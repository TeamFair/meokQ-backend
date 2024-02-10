package com.meokq.api.auth.request

import com.meokq.api.auth.enums.UserType
import com.meokq.api.user.response.UserResp
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Request")
data class AuthReq(
    @Schema(description = "User type (BOSS, CUSTOMER, ADMIN, UNKNOWN)")
    val userType: UserType = UserType.UNKNOWN,

    @Schema(description = "User ID")
    val userId: String? = null,
) {
    constructor(response: UserResp, userType: UserType) : this(
        userId = response.userId,
        userType = userType
    )
}
