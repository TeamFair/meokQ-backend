package com.meokq.api.redis

import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
//@Profile("local")
class RedisTokenServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val environment: Environment // 환경 정보를 주입
): RedisTokenService {

    private val environmentPrefix: String by lazy {
        environment.activeProfiles.firstOrNull() ?: "default" // 현재 활성화된 프로파일 가져오기
    }

    private val TOKEN_KEY_PREFIX = "token:$environmentPrefix:"

    override fun saveToken(userId: String, token: String) {
        val key = "$TOKEN_KEY_PREFIX$userId"
        redisTemplate.opsForValue().set(key, token, Duration.ofHours(1)) // 1시간 TTL
    }

    override fun getToken(userId: String): String? {
        val key = "$TOKEN_KEY_PREFIX$userId"
        return redisTemplate.opsForValue().get(key)
    }

    override fun deleteToken(userId: String) {
        val key = "$TOKEN_KEY_PREFIX$userId"
        redisTemplate.delete(key)
    }

    override fun deleteAllTokens() {
        val keys = redisTemplate.keys("$TOKEN_KEY_PREFIX*")
        redisTemplate.delete(keys!!)
    }
}