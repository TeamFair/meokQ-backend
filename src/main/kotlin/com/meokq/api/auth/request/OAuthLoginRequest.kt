package com.meokq.api.auth.request

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.OsType
import io.swagger.v3.oas.annotations.media.Schema

data class OAuthLoginRequest(
    @Schema(description = "\"APPLE\" 또는 \"GOOGLE\"")
    val provider: AuthChannel,
    @Schema(description = "\"IOS\" 또는 \"AOS\"")
    val osType: OsType,
    @Schema(description = "Provider ID Token")
    val idToken: String,
    @Schema(description = "FCM Push Token")
    val pushToken: String,
    @Schema(description = "기기 정보")
    val deviceUuid: String? = null,
)
