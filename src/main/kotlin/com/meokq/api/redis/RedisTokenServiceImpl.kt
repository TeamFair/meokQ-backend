package com.meokq.api.redis

import com.meokq.api.auth.dto.RefreshTokenInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
@Profile("!local")
class RedisTokenServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val environment: Environment // 환경 정보를 주입
): RedisTokenService {

    @Value("\${oauth.token.timeout.access-token}")
    private var accessTokenTimeout: Long = 0
    @Value("\${oauth.token.timeout.refresh-token}")
    private var refreshTokenTimeout: Long = 0

    private val environmentPrefix: String by lazy {
        environment.activeProfiles.firstOrNull() ?: "default" // 현재 활성화된 프로파일 가져오기
    }

    private val TOKEN_KEY_PREFIX = "token:$environmentPrefix:"
    private val REFRESH_TOKEN_KEY_PREFIX = "refresh_token:$environmentPrefix:"

    override fun saveToken(userId: String, token: String, refreshToken: String) {
        val key = "$TOKEN_KEY_PREFIX$userId"
        val refreshKey = "$REFRESH_TOKEN_KEY_PREFIX$userId"

        val refreshTokenMap = mapOf(
            "accessToken" to token,
            "refreshToken" to refreshToken
        )

        redisTemplate.opsForValue().set(key, token, Duration.ofHours(accessTokenTimeout))
        redisTemplate.opsForHash<String, String>().putAll(refreshKey, refreshTokenMap)
        redisTemplate.expire(refreshKey, Duration.ofHours(refreshTokenTimeout))
    }

    override fun getToken(userId: String): String? {
        val key = "$TOKEN_KEY_PREFIX$userId"
        return redisTemplate.opsForValue().get(key)
    }

    override fun deleteToken(userId: String) {
        val key = "$TOKEN_KEY_PREFIX$userId"
        val refreshKey = "$REFRESH_TOKEN_KEY_PREFIX$userId"
        redisTemplate.delete(listOf(key, refreshKey))
    }

    override fun deleteAllTokens() {
        val keys = redisTemplate.keys("$TOKEN_KEY_PREFIX*")
        val refreshKeys = redisTemplate.keys("$REFRESH_TOKEN_KEY_PREFIX*")
        redisTemplate.delete(keys)
        redisTemplate.delete(refreshKeys)
    }

    override fun getRefreshToken(userId: String): RefreshTokenInfo? {
        val key = "$REFRESH_TOKEN_KEY_PREFIX$userId"
        val tokenMap = redisTemplate.opsForHash<String, String>().entries(key)
        if (tokenMap.isEmpty()) {
            return null
        }
        return RefreshTokenInfo(tokenMap)
    }
}
