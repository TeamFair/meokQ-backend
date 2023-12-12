package com.meokq.api.challenge.controller

import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.challenge.service.ChallengeReviewService
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Challenge-Review", description = "도전 내역 검토")
@RestController
@RequestMapping("/api/challenges/review")
class ChallengeReviewController(
    private val service : ChallengeReviewService
) {
    @Operation(
        summary = "도전내역 검토결과 등록",
        description = "도전내역 검토결과를 등록합니다.",
    )
    @PutMapping
    fun review(@Valid @RequestBody request : ChallengeReviewReq) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.review(request)))
    }
}