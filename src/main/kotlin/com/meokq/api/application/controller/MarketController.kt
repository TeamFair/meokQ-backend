package com.meokq.api.application.controller

import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.MarketResponse
import com.meokq.api.application.service.MarketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/markets")
class MarketController {

    @Autowired
    lateinit var service : MarketService

    @GetMapping
    fun findAll(
        @RequestParam district : String,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResponse<MarketResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val searchDto = MarketSearchDto(district = district, pageable=pageable)
        val result = service.findAll(searchDto)
        return ResponseEntity.ok(BaseListResponse(result))
    }
}