package com.meokq.api.user.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.user.model.Agreement
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.request.AgreementSearchDto
import com.meokq.api.user.response.AgreementResp
import com.meokq.api.user.service.AgreementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@Tag(name = "Agreement", description = "약관동의내역")
@RestController
@RequestMapping("/api/agreement")
class AgreementController(
    private val service : AgreementService
) : BaseController<AgreementReq, AgreementResp, Agreement, String>{
    override val _service: BaseService<AgreementReq, AgreementResp, Agreement, String> = service

    @Operation(
        summary = "약관동의 내역 저장",
        description = "약관동의 내역 저장"
    )
    @PostMapping
    fun saveAll(@Valid @RequestBody request: List<AgreementReq>) : ResponseEntity<BaseResp> {
        val authentication = SecurityContextHolder.getContext().authentication
        val authDetails = authentication?.details as? AuthReq ?: throw Exception("Authentication data is invalid.")

        request.forEach { it.userId = authDetails.userId }
        val response = service.saveAll(request)
        return ResponseEntity.ok(BaseResp(null))
    }

    @Operation(
        summary = "약관동의 내역 조회",
        description = "약관동의 내역 조회",
        parameters = [
            //Parameter(name = "userId", description = "사용자 아이디(admin 일때)", required = false),
        ]
    )
    @GetMapping
    fun findAll(
        //@RequestParam userId : String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<BaseListResp<AgreementResp>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val authDetails = authentication?.details as? AuthReq ?: throw Exception("Authentication data is invalid.")

        val searchDto = AgreementSearchDto(
            userId = if (authDetails.userType != UserType.ADMIN) authDetails.userId else null,
        )

        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size)
        )

        return ResponseEntity.ok(
            BaseListResp(
                content = result.content,
                totalElements = result.totalElements,
                size = result.size,
                number = result.number
            )
        )
    }

}