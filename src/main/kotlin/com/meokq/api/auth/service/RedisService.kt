package com.meokq.api.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService(
    @Autowired
    private val redisTemplate : RedisTemplate<String, Any>
) {

    fun save(key: String, value: Any, duration: Duration){
        val values = redisTemplate.opsForValue()
        values.set(key, value, duration)
    }

    fun findByKey(key: String): Any?{
        val values = redisTemplate.opsForValue()
        return values.get(key)
    }

    fun deleteByKey(key: String) : Boolean {
        return redisTemplate.delete(key)
    }
}