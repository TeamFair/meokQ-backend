package com.meokq.api.redis

interface RedisTokenService {
    fun saveToken(userId: String, token: String)
    fun getToken(userId: String): String?
    fun deleteToken(userId: String)
    fun deleteAllTokens()
}