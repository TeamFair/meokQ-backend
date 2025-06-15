package com.meokq.api.auth.dto

data class RefreshTokenInfo(
    val accessToken: String,
    val refreshToken: String,
) {
    constructor(tokenMap: Map<String, String>) : this(
        accessToken = tokenMap["accessToken"] as String,
        refreshToken = tokenMap["refreshToken"] as String,
    )

    fun isValid(refreshToken: String, accessToken: String): Boolean {
        return refreshToken == this.refreshToken && accessToken == this.accessToken
    }
}
