package com.meokq.api.user.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.exception.TokenException
import com.meokq.api.user.annotaions.ExplainSelectCustomer
import com.meokq.api.user.annotaions.ExplainUpdateCustomer
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.service.CustomerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "User", description = "사용자 정보")
@Controller
@RequestMapping("/api")
class CustomerController(
    private val service: CustomerService
) {
    @ExplainSelectCustomer
    @GetMapping("/customer/user")
    fun findById(): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.findCustomerById(authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")))
    }

    @ExplainUpdateCustomer
    @PutMapping("/customer/user")
    fun updateCustomer(@RequestBody request : CustomerUpdateReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.update(
            userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다."),
            request = request
        ))
    }

    fun getAuthReq() : AuthReq {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            throw TokenException("권한 정보가 없습니다.")
        }
    }

    fun getRespEntity(resp : Any?, errorStatus : ErrorStatus = ErrorStatus.OK): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(resp))
    }
}