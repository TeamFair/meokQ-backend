package com.meokq.api.redis

interface RedisTokenService {
    fun getToken(userId: String): String?
    fun deleteToken(userId: String)
    fun deleteAllTokens()
    fun getRefreshToken(userId: String): String?
    fun saveToken(userId: String, token: String, refreshToken: String)
}
