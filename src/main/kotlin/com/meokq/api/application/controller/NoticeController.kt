package com.meokq.api.application.controller

import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.response.NoticeListResponse
import com.meokq.api.application.service.NoticeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notices")
class NoticeController {

    @Autowired
    lateinit var service: NoticeService

    @GetMapping
    fun findAll() : ResponseEntity<NoticeListResponse> {
        return ResponseEntity.ok(NoticeListResponse(service.findAll()))
    }

    @PostMapping
    fun save(@RequestBody request : NoticeRequest){
        service.save(request)
    }
}