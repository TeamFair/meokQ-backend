package com.meokq.api.redis

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.HashMap

@Tag(name = "healthCheck", description = "Redis 헬스체크용 컨트롤러")
@Controller
class RedisHealthCheckController(
    @Autowired private val redisConnectionFactory: RedisConnectionFactory
) {

    @GetMapping("/api/open/healthCheck/redis")
    fun open(): ResponseEntity<Map<String, Any>> {
        // Redis 설정값과 상태 확인
        val redisStatus = HashMap<String, Any>()
        try {
            if (redisConnectionFactory is LettuceConnectionFactory) {
                redisStatus.put("host", redisConnectionFactory.hostName)
                redisStatus.put("port", redisConnectionFactory.port)
                redisStatus.put("ssl", redisConnectionFactory.isUseSsl)
                redisStatus.put("timeout", redisConnectionFactory.timeout)
                redisStatus.put("password", if (redisConnectionFactory.password.isNullOrEmpty()) "not set" else "set")
            }

            // Ping Redis
            val connection = redisConnectionFactory.connection
            val pingResponse = connection.ping()
            redisStatus.put("ping", pingResponse?:"null")
            connection.close()

        } catch (e: Exception) {
            redisStatus.put("status", "DOWN")
            redisStatus.put("error", "e.message ?: \"Unknown error\"")
            return ResponseEntity.status(500).body(redisStatus)
        }

        redisStatus.put("status", "UP")
        return ResponseEntity.ok(redisStatus)
    }
}