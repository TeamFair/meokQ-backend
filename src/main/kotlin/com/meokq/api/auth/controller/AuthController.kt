package com.meokq.api.auth.controller

import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.service.AuthService
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증")
@RestController
@RequestMapping("/api")
class AuthController(
    private val service : AuthService
) {

    @Operation(
        summary = "(IAC001) login",
        description = "로그인 또는 회원가입",
    )
    @PostMapping("/open/login")
    fun login(@RequestBody request : LoginReq): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(
            BaseResp(service.login(request))
        )
    }

    @Operation(
        summary = "(IAC002) logout",
        description = "logout",
    )
    @GetMapping(value = [
        "/boss/logout", "/customer/logout", "/admin/logout"
    ])
    fun logout() : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.logout()))
    }

    @Operation(
        summary = "(IAC003) withdraw",
        description = "회원탈퇴 : 인증정보 및 회원 정보 삭제",
    )
    @GetMapping(value = [
        "/boss/withdraw", "/customer/withdraw", "/admin/withdraw"
    ])
    fun withdraw(): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.withdraw()))
    }
}