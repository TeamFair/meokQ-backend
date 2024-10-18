package com.meokq.api.test

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Tag(name = "healthCheck", description = "테스트용 컨트롤러")
@Controller
class HealthCheckController{

    @GetMapping("/api/open/healthCheck")
    fun open(): ResponseEntity<String> {
        return ResponseEntity.ok().body("OK")
    }

    ///
}