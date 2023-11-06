package com.meokq.api.application.controller

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.model.Boss
import com.meokq.api.application.request.BossRequest
import com.meokq.api.application.response.BaseResponse
import com.meokq.api.application.response.BossResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.BossService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Boss", description = "마켓 관리자")
@RestController
@RequestMapping("/api/boss")
class BossController(
    val service : BossService
    ) : BaseController<BossRequest, BossResponse, Boss, String>{
    override val _service: BaseService<BossRequest, BossResponse, Boss, String> = service

    @Operation(
        summary = "이메일로 보스정보 조회",
        description = "이메일로 보스정보를 조회합니다.",
        parameters = [
            Parameter(name = "email", description = "사용자 유형", required = true)
        ]
    )
    @GetMapping
    fun getBossByEmail(@RequestParam email: String) : ResponseEntity<BaseResponse>{
        val response = service.getBossByEmail(email)
        return ResponseEntity.ok(BaseResponse(response, ErrorStatus.OK))
    }
}