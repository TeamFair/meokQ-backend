package com.meokq.api.test.controller

import com.meokq.api.test.dto.TestDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/get")
    fun get(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("message" to "hello"))

    @PostMapping("/post")
    fun post(@RequestBody testDto: TestDto):ResponseEntity<TestDto> = ResponseEntity.ok(testDto)
}