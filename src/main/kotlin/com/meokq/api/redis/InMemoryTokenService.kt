package com.meokq.api.redis

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
@Profile("local")
class InMemoryTokenService : RedisTokenService {
    private val tokenStore: MutableMap<String, String> = ConcurrentHashMap()
    private val refreshTokenStore: MutableMap<String, String> = ConcurrentHashMap()

    override fun saveToken(userId: String, token: String, refreshToken: String) {
        val content = getToken(userId)
        if (content == null){
            tokenStore[userId] = token
        } else {
            tokenStore.replace(userId, token)
        }


        val refreshContent = getRefreshToken(userId)
        if (refreshContent == null){
            refreshTokenStore[userId] = refreshToken
        } else {
            refreshTokenStore.replace(userId, refreshToken)
        }

    }

    override fun getToken(userId: String): String? {
        return tokenStore[userId]
    }

    override fun getRefreshToken(userId: String): String? {
        return refreshTokenStore[userId]
    }

    override fun deleteToken(userId: String) {
        val content = getToken(userId)
        if (content != null) tokenStore.remove(userId)

        val refreshContent = getRefreshToken(userId)
        if (refreshContent != null) refreshTokenStore.remove(userId)
    }

    override fun deleteAllTokens() {
        tokenStore.clear()
        refreshTokenStore.clear()
    }
}
