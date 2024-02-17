package com.meokq.api.agreement.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.agreement.annotations.ExplainSaveAgreement
import com.meokq.api.agreement.annotations.ExplainSelectAgreement
import com.meokq.api.agreement.request.AgreementReq
import com.meokq.api.agreement.request.AgreementSearchDto
import com.meokq.api.agreement.service.AgreementService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// TODO : 약관동의 내역을 별도의 패키지로 변경하는 것을 고려
@Tag(name = "Agreement", description = "약관동의내역")
@RestController
@RequestMapping("/api")
class AgreementController(
    private val service : AgreementService
) : ResponseEntityCreation, AuthDataProvider {
    @ExplainSaveAgreement
    @PostMapping(value = ["/boss/agreement", "/customer/agreement"])
    fun saveAll(@Valid @RequestBody reqList: List<AgreementReq>) : ResponseEntity<BaseResp> {
        val result = service.saveAll(
            authReq = getAuthReq(),
            reqList = reqList
        )
        return getRespEntity(result)
    }

    @ExplainSelectAgreement
    @GetMapping(value = ["/boss/agreement", "/customer/agreement"])
    fun findAll(
        searchDto: AgreementSearchDto,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            authReq = getAuthReq(),
            searchDto = searchDto,
            pageable = PageRequest.of(page, size)
        )

        return getListRespEntity(result)
    }

}