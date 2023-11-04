package com.meokq.api.application.controller

import com.meokq.api.application.request.BossRequest
import com.meokq.api.application.response.BossResponse
import com.meokq.api.application.service.BossService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/boss")
class BossController {

    @Autowired
    lateinit var service : BossService

    @PostMapping
    fun save(@RequestBody request : BossRequest) : ResponseEntity<BossResponse> {
        val saveData = service.save(request)
        return ResponseEntity
            .created(URI.create("/markets/${saveData.bossId}"))
            .body(saveData)
    }
}