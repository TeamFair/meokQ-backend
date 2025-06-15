package com.meokq.api.redis

import com.meokq.api.auth.dto.RefreshTokenInfo
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
@Profile("local")
class InMemoryTokenService : RedisTokenService {
    private val tokenStore: MutableMap<String, String> = ConcurrentHashMap()
    private val refreshTokenStore: MutableMap<String, Map<String,String>> = ConcurrentHashMap()

    override fun saveToken(userId: String, token: String, refreshToken: String) {
        val content = getToken(userId)
        if (content == null){
            tokenStore[userId] = token
        } else {
            tokenStore.replace(userId, token)
        }

        val refreshTokenMap = mapOf(
            "accessToken" to token,
            "refreshToken" to refreshToken
        )

        val refreshContent = getRefreshToken(userId)
        if (refreshContent == null){
            refreshTokenStore[userId] = refreshTokenMap
        } else {
            refreshTokenStore.replace(userId, refreshTokenMap)
        }

    }

    override fun getToken(userId: String): String? {
        return tokenStore[userId]
    }

    override fun getRefreshToken(userId: String): RefreshTokenInfo? {
        if (refreshTokenStore[userId] == null) {
            return null
        }

        return RefreshTokenInfo(refreshTokenStore[userId]!!)
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
