package com.meokq.api.challenge.controller

import com.meokq.api.challenge.ChallengeXpProcessor
import com.meokq.api.challenge.annotations.ExplainDeleteChallenge
import com.meokq.api.challenge.annotations.ExplainRandomSelectChallengeList
import com.meokq.api.challenge.annotations.ExplainSaveChallenge
import com.meokq.api.challenge.annotations.ExplainSelectChallengeList
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.CreateChallengeResp
import com.meokq.api.challenge.response.ReadChallengeResp
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.xp.GrantXp
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RequestMapping("/api")
@RestController
class ChallengeController(
    private val service : ChallengeService
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainSelectChallengeList
    @GetMapping(value = ["/customer/challenge", "/boss/challenge"])
    fun findAll(
        searchDto: ChallengeSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq()
        )

        return getListRespEntity(result)
    }

    @ExplainSaveChallenge
    @PostMapping(value = ["/customer/challenge", ])
    @Transactional(rollbackFor = [Exception::class])
    @GrantXp(processor = ChallengeXpProcessor::class)
    fun save(@RequestBody @Valid request: ChallengeSaveReq): ResponseEntity<BaseResp> {
        val saveData = service.save(request, getAuthReq())
        return getRespEntity(CreateChallengeResp(saveData), ErrorStatus.CREATED)
    }

    @ExplainDeleteChallenge
    @DeleteMapping("/customer/{challengeId}")
    @Transactional(rollbackFor = [Exception::class])
    fun deleteById(@PathVariable challengeId: String) : ResponseEntity<BaseResp> {
        return getRespEntity(service.deleteById(challengeId))
    }

    @ExplainRandomSelectChallengeList
    @GetMapping(value = ["/customer/randomChallenge"])
    fun findRandomAll(
    ) : List<ReadChallengeResp> {
        val result = service.randomSelect()
        return result
    }
}