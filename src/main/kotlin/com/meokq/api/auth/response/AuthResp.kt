package com.meokq.api.auth.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Response")
data class AuthResp(
    @Schema(description = "JWT token for authorization")
    var authorization: String? = null,
)
