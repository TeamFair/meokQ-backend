package com.meokq.api.application.controller

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.ChallengeService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/challenges")
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeRequest, ChallengeResponse, Challenge, String>{
    override val _service: BaseService<ChallengeRequest, ChallengeResponse, Challenge, String> = service

    @GetMapping
    fun findAll(
        @RequestParam(required = true) marketId : String,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResponse<ChallengeResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findByMarketId(marketId, pageable)
        return ResponseEntity.ok(BaseListResponse(result))
    }

}