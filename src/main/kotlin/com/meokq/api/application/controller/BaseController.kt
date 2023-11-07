package com.meokq.api.application.controller

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.response.BaseResponse
import com.meokq.api.application.service.BaseService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody

interface BaseController<REQ, RES, MODEL, ID> {
    val _service : BaseService<REQ, RES, MODEL, ID>

    @ApiResponse(
        responseCode = "200",
        description = "성공적으로 등록된 경우",
        content = [Content(schema = Schema(implementation = BaseResponse::class))]
    )
    fun save(@Validated @RequestBody request: REQ) : ResponseEntity<BaseResponse> {
        val saveData = _service.save(request)
        return ResponseEntity.ok(BaseResponse(saveData, ErrorStatus.CREATED))
    }
}