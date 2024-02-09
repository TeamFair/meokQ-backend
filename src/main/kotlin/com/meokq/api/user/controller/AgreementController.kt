package com.meokq.api.user.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.user.annotaions.ExplainSaveAgreement
import com.meokq.api.user.annotaions.ExplainSelectAgreement
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.request.AgreementSearchDto
import com.meokq.api.user.service.AgreementService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//@Tag(name = "Agreement", description = "약관동의내역")
@RestController
@RequestMapping("/api")
class AgreementController(
    private val service : AgreementService
) : ResponseEntityCreation, AuthDataProvider {
    @ExplainSaveAgreement
    @PostMapping(value = ["/boss/agreement", "/customer/agreement"])
    fun saveAll(@Valid @RequestBody request: List<AgreementReq>) : ResponseEntity<BaseResp> {
        val authDetails = getAuthReq()
        request.forEach { it.userId = authDetails.userId }
        val response = service.saveAll(request)
        return ResponseEntity.ok(BaseResp(null))
    }

    @ExplainSelectAgreement
    @GetMapping(value = ["/boss/agreement", "/customer/agreement"])
    fun findAll(
        searchDto: AgreementSearchDto,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<BaseListRespV2> {
        val authDetails = getAuthReq()

        val result = service.findAll(
            searchDto = searchDto.also {
                it.userId = if (authDetails.userType != UserType.ADMIN) authDetails.userId else null
            },
            pageable = PageRequest.of(page, size)
        )

        return getListRespEntity(result)
    }

}