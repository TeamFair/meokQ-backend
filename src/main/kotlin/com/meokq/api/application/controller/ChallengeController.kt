package com.meokq.api.application.controller

import com.meokq.api.application.enums.ChallengeStatus
import com.meokq.api.application.model.Challenge
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.BaseResponse
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.ChallengeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RestController
@RequestMapping("api/challenges")
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeRequest, ChallengeResponse, Challenge, String>{
    override val _service: BaseService<ChallengeRequest, ChallengeResponse, Challenge, String> = service

    @Operation(
        summary = "도전내역 조회",
        description = "마켓에서 등록한 진행상태별 도전내역을 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "market ID", required = true),
            Parameter(name = "status", description = "상태값", required = true),
        ]
    )
    @GetMapping
    fun findAllByMarketIdAndStatus(
        @RequestParam(required = true) marketId : String,
        @RequestParam(required = true) status : ChallengeStatus,
        /*@RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,*/
        // TODO : 추후 페이징 반영
    ) : ResponseEntity<BaseListResponse<ChallengeResponse>> {
        //val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAllByMarketIdAndStatus(marketId, status)
        return ResponseEntity.ok(BaseListResponse(
            content = result.toMutableList(),
            totalElements = result.size.toLong(),
            size = result.size,
            number = 0
        ))
    }

    @Operation(
        summary = "도전 내역 등록",
        description = "도전 내역를 등록합니다.",
    )
    @PostMapping
    override fun save(request: ChallengeRequest): ResponseEntity<BaseResponse> {
        return super.save(request)
    }

    // 반려하기, 발급하기

}