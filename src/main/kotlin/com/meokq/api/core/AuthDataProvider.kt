package com.meokq.api.core

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.exception.TokenException
import org.springframework.security.core.context.SecurityContextHolder

interface AuthDataProvider {
    fun getAuthReq() : AuthReq {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            throw TokenException("권한 정보가 없습니다.")
        }
    }
}