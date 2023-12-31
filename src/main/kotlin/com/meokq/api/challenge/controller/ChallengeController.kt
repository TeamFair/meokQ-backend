package com.meokq.api.challenge.controller

import com.meokq.api.challenge.annotations.ExplainDeleteChallenge
import com.meokq.api.challenge.annotations.ExplainSaveChallenge
import com.meokq.api.challenge.annotations.ExplainSelectChallengeList
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.response.CreateChallengeResp
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.service.BaseService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RequestMapping("/api")
@RestController
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeReq, ChallengeResp, Challenge, String> {
    override val _service: BaseService<ChallengeReq, ChallengeResp, Challenge, String> = service

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

        return getListRespEntityV2(result)
    }

    @ExplainSaveChallenge
    @PostMapping(value = ["/customer/challenge", ])
    fun save(@RequestBody @Valid request: ChallengeSaveReq): ResponseEntity<BaseResp> {
        val saveData = service.save(request, getAuthReq())
        return getRespEntity(CreateChallengeResp(saveData), ErrorStatus.CREATED)
    }

    @ExplainDeleteChallenge
    @DeleteMapping("/customer/{challengeId}")
    override fun deleteById(@PathVariable challengeId: String) : ResponseEntity<BaseResp> {
        return super.deleteByIdWithAuth(challengeId)
    }
}