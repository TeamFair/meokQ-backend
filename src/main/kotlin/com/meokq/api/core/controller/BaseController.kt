package com.meokq.api.core.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.exception.AccessDeniedException
import com.meokq.api.core.exception.TokenException
import com.meokq.api.core.service.BaseService
import org.springframework.data.domain.PageImpl
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

    fun findById(id : ID) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(_service.findById(id)))
    }

    fun deleteById(id : ID) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(_service.deleteById(id)))
    }

    fun deleteByIdWithAuth(id : ID) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(_service.deleteById(id)))
    }

    fun getAuthReq() : AuthReq {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            throw TokenException("권한 정보가 없습니다.")
        }
    }

    fun getListRespEntityV2(page: PageImpl<RES>): ResponseEntity<BaseListRespV2> {
        return ResponseEntity.ok(
            BaseListRespV2(
                content = page.content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getListRespEntity(page: PageImpl<RES>): ResponseEntity<BaseListResp<Any?>> {
        return ResponseEntity.ok(
            BaseListResp(
                content = page.content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getRespEntity(resp : Any): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(resp))
    }

    fun checkAccess(userTypeList : List<UserType> = listOf(), isOpen : Boolean = false) {
        if (isOpen) return // 모든타입의 사용자에게 허용된 리소스

        getAuthReq().let {req ->
            if (userTypeList.none { req.userType == it }){
                throw AccessDeniedException("${req.userType}타입의 사용자에게 허용되지 않은 리소스입니다.")
            }
        }
    }
}