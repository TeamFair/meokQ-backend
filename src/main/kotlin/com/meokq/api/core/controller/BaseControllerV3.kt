package com.meokq.api.core.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.exception.TokenException
import com.meokq.api.core.util.ValidationUtil
import jakarta.xml.bind.ValidationException
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder

interface BaseControllerV3 {
    /**
     * get authData
     */
    fun getAuthReq() : AuthReq {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            throw TokenException("권한 정보가 없습니다.")
        }
    }

    /**
     * create response
     */
    fun getListRespEntity(page: Page<*>): ResponseEntity<BaseListRespV2> {
        return ResponseEntity.ok(
            BaseListRespV2(
                content = page.content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getRespEntity(
        result : Any?,
        errorStatus : ErrorStatus = ErrorStatus.OK
    ): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(result))
    }

    /**
     * check validation
     */
    fun checkNotNullData(value : Any?, message : String){
        ValidationUtil.checkNotNullData(
            value, ValidationException("Parameter 형식오류 : $message")
        )
    }
}