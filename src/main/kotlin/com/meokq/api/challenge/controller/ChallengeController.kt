package com.meokq.api.challenge.controller

import com.meokq.api.challenge.annotations.*
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
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
    @GetMapping(value = ["/customer/challenge", "/boss/challenge","/admin/challenge"])
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
    fun save(@RequestBody @Valid request: ChallengeSaveReq): ResponseEntity<BaseResp> {
        val saveData = service.save(request, getAuthReq())
        return getRespEntity(CreateChallengeResp(saveData), ErrorStatus.CREATED)
    }

    @ExplainDeleteChallenge
    @DeleteMapping("/customer/{challengeId}","/admin/{challengeId}")
    @Transactional(rollbackFor = [Exception::class])
    fun deleteById(@PathVariable challengeId: String) : ResponseEntity<BaseResp> {
        return getRespEntity(service.deleteById(challengeId))
    }

    @ExplainUpdateStatusChallenge
    @PatchMapping("/customer/report","/admin/report")
    @Transactional(rollbackFor = [Exception::class])
    fun updateStatus(@RequestParam challengeId: String,
               @RequestParam status: String) : ResponseEntity<BaseResp> {
        return getRespEntity(service.updateStatus(challengeId,status,getAuthReq()))
    }

    @ExplainGetReportedChallenges
    @GetMapping("/admin/report")
    @Transactional(rollbackFor = [Exception::class])
    fun getReportedChallengeList( @RequestParam(defaultValue = "0") page : Int,
                               @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.getReportedChallengeList(pageable = PageRequest.of(page, size)))
    }

    @ExplainRandomSelectChallengeList
    @GetMapping(value = ["/customer/randomChallenge"])
    fun findRandomAll(
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2>  {
        val result = service.findRandomAll(
            pageable = PageRequest.of(page, size)
        )
        return getListRespEntity(result)
    }

    @ExplainIncreaseViewCount
    @PostMapping(value = ["/customer/viewCount"])
    fun increaseViewCount(
        @RequestParam challengeId : String
    ): ResponseEntity<BaseResp> {
        return getRespEntity(service.increaseViewCount(
            id = challengeId,
            authReq = getAuthReq())
        )
    }


}