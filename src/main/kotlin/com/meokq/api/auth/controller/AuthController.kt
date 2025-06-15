package com.meokq.api.auth.controller

import com.meokq.api.auth.annotations.*
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.request.OAuthLoginRequest
import com.meokq.api.auth.request.OAuthRefreshRequest
import com.meokq.api.auth.service.AuthService
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Auth", description = "인증")
@RequestMapping("/api")
@RestController
class AuthController(
    private val service: AuthService,
) : AuthDataProvider {

    @ExplainLogin
    @PostMapping("/open/login")
    fun login(@RequestBody request: LoginReq): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(
            BaseResp(service.login(request))
        )
    }

    @ExplainLoginOAuth
    @PostMapping("/open/login/oauth")
    fun verifyOAuthToken(@RequestBody request: OAuthLoginRequest): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(
            BaseResp(service.login(request))
        )
    }

    @ExplainLoginOAuthRefresh
    @PostMapping("/open/login/oauth/refresh")
    fun verifyRefreshToken(@RequestBody request: OAuthRefreshRequest): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(
            BaseResp(service.refresh(request))
        )
    }

    @ExplainLogout
    @GetMapping(value = ["/boss/logout", "/customer/logout"])
    fun logout(): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.logout(getAuthReq())))
    }


    @ExplainWithdraw
    @GetMapping(value = ["/boss/withdraw", "/customer/withdraw"])
    fun withdraw(): ResponseEntity<BaseResp> {
        service.withdraw(getAuthReq())

        return ResponseEntity.ok(BaseResp(emptyMap<String, String>()))
    }
}
