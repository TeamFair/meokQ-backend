package com.meokq.api.core.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverterV2
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.service.BaseServiceV2
import com.meokq.api.core.util.ValidationUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.xml.bind.ValidationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.reflect.KClass

@Deprecated("version update: AuthDataProvider 등 세분화된 인터페이스 사용을 권장")
@RequestMapping("/api")
interface BaseControllerV2<MODEL, ID> {
    val _service : BaseServiceV2<MODEL, ID>
    val authPrincipal : AuthPrincipal

    /**
     * base - curd
     */
    fun <DTO>save(
        @RequestBody request: DTO,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseResp> {
        val result = _service.save(
            reqSave = request,
            authReq = getAuthReq(),
            needAuthCheck = authPrincipal.needAuthCheck(httpRequest),
        )

        return getRespEntity(
            result = result,
            respClass = respClass,
            errorStatus = ErrorStatus.CREATED,
        )
    }

    fun <DTO>saveAll(
        @RequestBody reqSaves : List<DTO>,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseResp> {
        val result = _service.save(
            reqSave = reqSaves,
            authReq = getAuthReq(),
            needAuthCheck = authPrincipal.needAuthCheck(httpRequest),
        )

        return return getRespEntity(
            result = result,
            respClass = respClass,
            errorStatus = ErrorStatus.CREATED,
        )
    }

    fun <DTO_SAVE, DTO_SEARCH>update(
        @RequestBody reqSave: DTO_SAVE,
        @RequestBody reqSearch: DTO_SEARCH,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseResp> {
        val result = _service.update(
            reqSave = reqSave,
            reqSearch = reqSearch,
            authReq = getAuthReq(),
            needAuthCheck = authPrincipal.needAuthCheck(httpRequest),
        )

        return return getRespEntity(
            result = result,
            respClass = respClass,
        )
    }

    fun findById(
        @PathVariable id : ID,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseResp> {
        val result = _service.findById(
            id = id,
            authReq = getAuthReq(),
            needAuthCheck = authPrincipal.needAuthCheck(httpRequest),
        )

        return return getRespEntity(
            result = result,
            respClass = respClass,
        )
    }

    fun <DTO>findAll(
        reqSearch: DTO,
        page : Int,
        size : Int,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseListRespV2>{
        val needAuthCheck = authPrincipal.needAuthCheck(httpRequest)
        val result =
            _service.findAll(
            reqSearch = reqSearch,
            authReq = getAuthReq(),
            pageable = PageRequest.of(page, size),
            needAuthCheck = needAuthCheck,
        )
        return getListRespEntityV2(
            page = result,
            respClass = respClass,
        )
    }

    fun deleteById(
        @PathVariable id : ID,
        httpRequest: HttpServletRequest,
        respClass: KClass<*>,
    ) : ResponseEntity<BaseResp> {
        val result = _service.deleteById(
            id = id,
            authReq = getAuthReq(),
            needAuthCheck = authPrincipal.needAuthCheck(httpRequest),
        )

        return return getRespEntity(
            result = result,
            respClass = respClass,
        )
    }

    /**
     * get authData
     */
    fun getAuthReq() : AuthReq? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication?.principal is AuthReq) {
            authentication.principal as AuthReq
        } else {
            //if (throwException) throw TokenException("권한 정보가 없습니다.")
            return null
        }
    }

    /**
     * create response
     */
    fun getListRespEntityV2(
        page: Page<*>,
        respClass: KClass<*>
    ): ResponseEntity<BaseListRespV2> {
        val converter = BaseConverterV2(respClass)
        val content = page.content.map { converter.convertTo(it) }

        return ResponseEntity.ok(
            BaseListRespV2(
                content = content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getRespEntity(
        result : Any?,
        respClass: KClass<*>,
        errorStatus : ErrorStatus = ErrorStatus.OK
    ): ResponseEntity<BaseResp> {
        val converter = BaseConverterV2(respClass)
        val resp = converter.convertTo(result)
        return ResponseEntity.ok(BaseResp(resp))
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