package com.meokq.api.redis

import com.meokq.api.auth.dto.RefreshTokenInfo

interface RedisTokenService {
    fun getToken(userId: String): String?
    fun deleteToken(userId: String)
    fun deleteAllTokens()
    fun getRefreshToken(userId: String): RefreshTokenInfo?
    fun saveToken(userId: String, token: String, refreshToken: String)
}
