package com.meokq.api.core.converter

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.ChallengeResponse
import org.springframework.stereotype.Component

@Component
class ChallengeConverter : BaseConverter<ChallengeRequest, ChallengeResponse, Challenge>{
    override fun modelToResponse(model: Challenge): ChallengeResponse {
        return ChallengeResponse(
            challengeId = model.challengeId,
            receiptImage = model.receiptImage,
            quest = null,
        )
    }

    override fun requestToModel(request: ChallengeRequest): Challenge {
        return Challenge(
            questId = request.questId,
            customerId = request.customerId, //TODO : 추후 제거
            receiptImage = request.receiptImage,
        )
    }
}