package com.meokq.api.application.service

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.model.ChallengeId
import com.meokq.api.application.repository.ChallengeRepository
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.ChallengeConverter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val converter : ChallengeConverter,
    private val repository: ChallengeRepository
) : BaseService<ChallengeRequest, ChallengeResponse, Challenge, ChallengeId> {
    override var _converter: BaseConverter<ChallengeRequest, ChallengeResponse, Challenge> = converter
    override var _repository: JpaRepository<Challenge, ChallengeId> = repository
}