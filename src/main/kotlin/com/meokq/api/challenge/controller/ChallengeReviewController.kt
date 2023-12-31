package com.meokq.api.challenge.controller

import com.meokq.api.challenge.annotations.ExplainReviewChallenge
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.challenge.service.ChallengeReviewService
import com.meokq.api.core.dto.BaseResp
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ChallengeReviewController(
    private val service : ChallengeReviewService
) {

    @ExplainReviewChallenge
    @PutMapping("/boss/challenge/review")
    fun review(@Valid @RequestBody request : ChallengeReviewReq) : ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(service.review(request)))
    }
}