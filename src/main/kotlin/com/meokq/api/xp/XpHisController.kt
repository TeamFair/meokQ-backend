package com.meokq.api.xp

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "XP", description = "경험치")
class XpHisController(
    private val service: XpHisService
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainSelectXpList
    @GetMapping(value = ["/customer/xp"])
    fun findAll(
        searchDto: XpSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ): ResponseEntity<BaseResp> {
        val result = service.findAll(
            searchDto = searchDto.also { it.userId = getAuthReq().userId },
            pageable = PageRequest.of(page, size)
        )
        return getRespEntity(result)
    }
}