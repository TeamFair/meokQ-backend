package com.meokq.api.core.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.exception.TokenException
import com.meokq.api.core.service.BaseService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody

//@RequestMapping(value = ["/api/"])
interface BaseController<REQ, RES, MODEL, ID> {
    val _service : BaseService<REQ, RES, MODEL, ID>

    /**
     * base - curd
     */
    fun save(@Validated @RequestBody request: REQ) : ResponseEntity<BaseResp> {
        val saveData = _service.save(request)
        return getRespEntity(saveData, ErrorStatus.CREATED)
    }

    fun findById(id : ID) : ResponseEntity<BaseResp> {
        return getRespEntity(_service.findById(id))
    }

    fun deleteById(id : ID) : ResponseEntity<BaseResp> {
        return getRespEntity(_service.deleteById(id))
    }

    /**
     * withAuth - curd
     */
    fun deleteByIdWithAuth(id : ID) : ResponseEntity<BaseResp> {
        val resp = _service.deleteByIdWithAuth(id, getAuthReq())
        return getRespEntity(resp)
    }

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
    fun getListRespEntityV2(page: Page<RES>): ResponseEntity<BaseListRespV2> {
        return ResponseEntity.ok(
            BaseListRespV2(
                content = page.content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getRespEntity(resp : Any?, errorStatus : ErrorStatus = ErrorStatus.OK): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(resp))
    }
}