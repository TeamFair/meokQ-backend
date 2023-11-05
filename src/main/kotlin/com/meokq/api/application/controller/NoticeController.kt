package com.meokq.api.application.controller

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.model.Notice
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.NoticeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notices")
class NoticeController(
    final val service: NoticeService
) : BaseController<NoticeRequest, NoticeResponse, Notice, String> {
    override val _service: BaseService<NoticeRequest, NoticeResponse, Notice, String> = service

    @GetMapping
    fun findAll(
        @RequestParam target : UserType,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResponse<NoticeResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAll(target, pageable)
        return ResponseEntity.ok(BaseListResponse(result))
    }
}