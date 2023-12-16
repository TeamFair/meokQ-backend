package com.meokq.api.core.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.service.BaseService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody

interface BaseController<REQ, RES, MODEL, ID> {
    val _service : BaseService<REQ, RES, MODEL, ID>

    fun save(@Validated @RequestBody request: REQ) : ResponseEntity<BaseResp> {
        val saveData = _service.save(request)
        return ResponseEntity.ok(BaseResp(saveData, ErrorStatus.CREATED))
    }

    fun deleteById(id : ID) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(_service.deleteById(id)))
    }

    fun getAuthReq() : AuthReq {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            throw Exception("권한 정보가 없습니다.")
        }
    }
}