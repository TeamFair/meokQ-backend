package com.meokq.api.auth.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Response")
data class AuthResp(
    @Schema(description = "access token")
    var authorization: String? = null,
    @Schema(description = "refresh token")
    var refreshToken: String? = null,
)
