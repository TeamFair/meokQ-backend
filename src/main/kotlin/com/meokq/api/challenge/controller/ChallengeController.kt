package com.meokq.api.challenge.controller

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeApproveReq
import com.meokq.api.challenge.request.ChallengeRejectReq
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RestController
@RequestMapping("api/challenges")
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeReq, ChallengeResp, Challenge, String> {
    override val _service: BaseService<ChallengeReq, ChallengeResp, Challenge, String> = service

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
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<ChallengeResp>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAllByMarketIdAndStatus(marketId, status, pageable)
        return ResponseEntity.ok(
            BaseListResp(
            content = result.toMutableList(),
            totalElements = result.size.toLong(),
            size = result.size,
            number = 0
        )
        )
    }

    @Operation(
        summary = "도전 내역 등록",
        description = "도전 내역를 등록합니다.",
    )
    @PostMapping
    override fun save(@Valid request: ChallengeReq): ResponseEntity<BaseResp> {
        return super.save(request)
    }

    @Operation(
        summary = "도전 내역 승인",
        description = "도전 내역을 승인합니다.",
    )
    @PutMapping("/{challengeId}/approve")
    fun approve(@Valid @RequestBody request : ChallengeApproveReq) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.approve(request)))
    }

    @Operation(
        summary = "도전 내역 반려",
        description = "도전 내역을 반려합니다.",
    )
    @PutMapping("/{challengeId}/reject")
    fun reject(@Valid @RequestBody request : ChallengeRejectReq) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.reject(request)))
    }
}