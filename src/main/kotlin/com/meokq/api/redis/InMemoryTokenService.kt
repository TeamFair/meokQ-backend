package com.meokq.api.redis

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class InMemoryTokenService : RedisTokenService {
    private val tokenStore: MutableMap<String, String> = ConcurrentHashMap()

    override fun saveToken(userId: String, token: String) {
        val content = getToken(userId)
        if (content == null){
            tokenStore.put(userId, token)
            return
        }

        tokenStore.replace(userId, token)
    }

    override fun getToken(userId: String): String? {
        return tokenStore[userId]
    }

    override fun deleteToken(userId: String) {
        val content = getToken(userId)
        if (content != null) tokenStore.remove(userId)
    }

    override fun deleteAllTokens() {
        tokenStore.clear()
    }
}