package com.meokq.api.auth.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Request")
data class LoginReq(
    @Schema(description = "User type (BOSS, CUSTOMER, ADMIN)")
    val userType: UserType,

    @Schema(description = "Access token for authentication", example = "accessToken")
    val accessToken: String,

    @Schema(description = "Refresh token for token refreshing (optional)", example = "refreshToken")
    val refreshToken: String?,

    @Schema(description = "User email", example = "email@sample.com")
    var email: String,

    @Schema(description = "Authentication channel (e.g., GOOGLE, FACEBOOK)")
    val channel: AuthChannel
){
    @JsonIgnore
    var userId : String? = null
}
