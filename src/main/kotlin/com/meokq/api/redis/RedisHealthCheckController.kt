package com.meokq.api.redis

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Tag(name = "healthCheck", description = "테스트용 컨트롤러")
@Controller
class RedisHealthCheckController(
    @Autowired private val redisTokenServiceImpl: RedisTokenServiceImpl
) {
    @GetMapping("/api/open/healthCheck/redis")
    fun open(): ResponseEntity<String> {
        redisTokenServiceImpl.saveToken(userId = "testUser", token = "testToken")
        return ResponseEntity.ok().body("OK")
    }
}