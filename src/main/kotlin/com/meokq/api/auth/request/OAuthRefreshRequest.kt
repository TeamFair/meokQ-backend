package com.meokq.api.auth.request

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.OsType
import io.swagger.v3.oas.annotations.media.Schema

data class OAuthRefreshRequest(
    @Schema(description = "Access Token")
    val accessToken: String,
    @Schema(description = "Refresh Token")
    val refreshToken: String,
)
