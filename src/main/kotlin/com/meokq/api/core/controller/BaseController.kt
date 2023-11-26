package com.meokq.api.core.controller

import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
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
        content = [Content(schema = Schema(implementation = BaseResp::class))]
    )
    fun save(@Validated @RequestBody request: REQ) : ResponseEntity<BaseResp> {
        val saveData = _service.save(request)
        return ResponseEntity.ok(BaseResp(saveData, ErrorStatus.CREATED))
    }
}