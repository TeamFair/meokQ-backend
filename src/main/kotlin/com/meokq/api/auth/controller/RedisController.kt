package com.meokq.api.auth.controller

import com.meokq.api.auth.service.RedisService
import com.meokq.api.core.converter.DateTimeConverter
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Tag(name = "TEST")
@Controller
@RequestMapping("/api")
class RedisController(
    @Autowired
    private val service : RedisService,
    @Autowired
    private val dateTimeConverter: DateTimeConverter,
) {

    @Operation(
        summary = "(ITR) test redis connect",
        description = "test redis connect",
    )
    @GetMapping("/open/redis/test")
    fun test() : ResponseEntity<BaseResp> {
        val key = "test-key" + UUID.randomUUID()
        val value = "my-value" + dateTimeConverter.convertToString(LocalDateTime.now())
        val duration = Duration.ofMinutes(1)
        service.save(key, value, duration)

        val result = service.findByKey(key)

        return ResponseEntity.ok(
            BaseResp("key = '$key', value = '$result'")
        )
    }
}