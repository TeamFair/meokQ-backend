package com.meokq.api.application.controller

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.model.ChallengeId
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.application.service.BaseService
import com.meokq.api.application.service.ChallengeService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/challenges")
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeRequest, ChallengeResponse, Challenge, ChallengeId>{
    override val _service: BaseService<ChallengeRequest, ChallengeResponse, Challenge, ChallengeId> = service
}