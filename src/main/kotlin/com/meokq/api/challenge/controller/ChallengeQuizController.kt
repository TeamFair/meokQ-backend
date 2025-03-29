package com.meokq.api.challenge.controller

import com.meokq.api.challenge.annotations.ExplainSaveChallengeQuiz
import com.meokq.api.challenge.annotations.ExplainSelectChallengeQuiz
import com.meokq.api.challenge.request.ChallengeQuizReq
import com.meokq.api.challenge.request.ChallengeQuizSearchDto
import com.meokq.api.challenge.service.ChallengeQuizService
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Challenge", description = "도전 내역")
@Controller
class ChallengeQuizController(
    val challengeQuizService: ChallengeQuizService
) : ResponseEntityCreation, AuthDataProvider {
    /**
     * 퀴즈 타입의 챌린지를 생성합니다.
     */
    @ExplainSaveChallengeQuiz
    @PostMapping("/api/customer/challenge/quiz")
    fun createQuizChallenge(@RequestBody req: ChallengeQuizReq): ResponseEntity<BaseResp> {
        val resp = BaseResp(challengeQuizService.createQuizChallenge(req, getAuthReq()))
        return ResponseEntity.ok(resp)
    }

    /**
     * 퀴즈 타입의 챌린지를 조회합니다.
     */
    @ExplainSelectChallengeQuiz
    @GetMapping("/api/customer/challenges/quiz")
    fun quizChallengeList(dto: ChallengeQuizSearchDto,
                          @RequestParam(defaultValue = "0") page : Int,
                          @RequestParam(defaultValue = "10") size : Int): ResponseEntity<BaseListRespV2> {
        val authReq = getAuthReq()
        val quizChallengeList = challengeQuizService.quizChallengeList(
            dto = dto,
            pageable = PageRequest.of(page, size),
            userId = authReq.userId
        )
        return getListRespEntity(quizChallengeList)
    }
}