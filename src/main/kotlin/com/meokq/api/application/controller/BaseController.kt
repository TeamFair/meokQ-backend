package com.meokq.api.application.controller

import com.meokq.api.application.service.BaseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI

interface BaseController<REQ, RES, MODEL, ID> {
    val _service : BaseService<REQ, RES, MODEL, ID>

    @PostMapping
    fun save(@RequestBody request: REQ) : ResponseEntity<Any> {
        val saveData = _service.save(request)
        return ResponseEntity
            .created(URI.create(""))
            .body(saveData)
    }
}