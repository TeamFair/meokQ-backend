package com.meokq.api.challenge.controller

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.request.ChallengeSearchDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RestController
@RequestMapping("/api/challenges")
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeReq, ChallengeResp, Challenge, String> {
    override val _service: BaseService<ChallengeReq, ChallengeResp, Challenge, String> = service

    @Operation(
        summary = "도전내역 조회",
        description = "마켓에서 등록한 진행상태별 도전내역을 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "market ID", required = false),
            Parameter(name = "status", description = "상태값", required = false),
        ]
    )
    @GetMapping
    fun findAll(
        @RequestParam(required = false) marketId : String?,
        @RequestParam(required = false) status : ChallengeStatus?,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<ChallengeResp>> {
        val result = service.findAll(
            searchDto = ChallengeSearchDto(
                marketId = marketId,
                status = status,
            ),
            pageable = PageRequest.of(page, size)
        )

        return ResponseEntity.ok(
            BaseListResp(
            content = result.content,
            totalElements = result.totalElements,
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
        summary = "도전내역 검토결과 등록",
        description = "도전내역 검토결과를 등록합니다.",
    )
    @PutMapping("/review")
    fun reject(@Valid @RequestBody request : ChallengeReviewReq) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.review(request)))
    }
}