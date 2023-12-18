package com.meokq.api.test

import com.meokq.api.core.controller.AuthPrincipal
import com.meokq.api.core.controller.BaseControllerV2
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseServiceV2
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController(
    private val service: TestService
) : BaseControllerV2<TestModel, String>{

    override val _service: BaseServiceV2<TestModel, String> = service
    override val authPrincipal: AuthPrincipal = object : AuthPrincipal {}

    @GetMapping("/get")
    fun get(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("message" to "hello"))

    @PostMapping
    fun save(
        @RequestBody request: TestDto,
        httpRequest: HttpServletRequest
    ): ResponseEntity<BaseResp> {
        return super.save(request, httpRequest, TestRespD::class)
    }

    @DeleteMapping("/{testId}")
    fun deleteById(
        @PathVariable testId: String,
        httpRequest: HttpServletRequest
    ): ResponseEntity<BaseResp> {
        return super.deleteById(testId, httpRequest, TestRespD::class)
    }

    @GetMapping
    fun findAll(
        reqSearch: TestDto,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        httpRequest: HttpServletRequest
    ): ResponseEntity<BaseListRespV2>{
        return super.findAll(reqSearch, page, size, httpRequest, TestRespD::class)
    }

    @PutMapping
     fun update(
        @Validated @RequestBody reqSave: TestDto,
        @Validated @RequestBody reqSearch: TestDto,
        httpRequest: HttpServletRequest
    ): ResponseEntity<BaseResp> {
        return super.update(reqSave, reqSearch, httpRequest, TestRespD::class)
    }
}