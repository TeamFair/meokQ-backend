package com.meokq.api.application.controller

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.request.NoticeSearchDto
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.application.service.NoticeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notices")
class NoticeController {

    @Autowired
    lateinit var service: NoticeService

    @GetMapping
    fun findAll(
        @RequestParam target : UserType,
        @RequestParam(defaultValue = "1") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<Page<NoticeResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val searchDto = NoticeSearchDto(target = target, pageable=pageable)
        return ResponseEntity.ok(service.findAll(searchDto))
    }

    @PostMapping
    fun save(@RequestBody request : NoticeRequest){
        service.save(request)
    }
}