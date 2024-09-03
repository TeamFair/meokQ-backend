package com.meokq.api.xp.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.xp.annotations.ExplainSelectXpHistory
import com.meokq.api.xp.annotations.ExplainSelectXpStats
import com.meokq.api.xp.dto.request.XpSearchDto
import com.meokq.api.xp.service.XpHistoryService
import com.meokq.api.xp.service.XpService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "Xp", description = "활동로그")
class XpController(
    private val service: XpService,
    private val xpHistoryService: XpHistoryService
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainSelectXpHistory
    @GetMapping(value = ["/customer/xpHistory"])
    fun findAll(
        searchDto: XpSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ): ResponseEntity<BaseListRespV2> {
        val result = xpHistoryService.findAll(
            searchDto = searchDto.also { it.userId = getAuthReq().userId },
            pageable = PageRequest.of(page, size)
        )
        return getListRespEntity(result)
    }

    @ExplainSelectXpStats
    @GetMapping(value = ["/customer/xpStats"])
    fun fetchStats() : ResponseEntity<BaseResp> {
        return getRespEntity(service.fetchStats(getAuthReq()))
    }
}