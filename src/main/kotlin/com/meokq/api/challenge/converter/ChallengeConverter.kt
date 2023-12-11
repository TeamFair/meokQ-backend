package com.meokq.api.challenge.converter

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.core.converter.BaseConverter
import org.springframework.stereotype.Component

@Component
class ChallengeConverter : BaseConverter<ChallengeReq, ChallengeResp, Challenge> {
    override fun modelToResponse(model: Challenge): ChallengeResp {
        return ChallengeResp(
            challengeId = model.challengeId,
            receiptImage = model.receiptImage,
            quest = null,
            status = model.status
        )
    }

    override fun requestToModel(request: ChallengeReq): Challenge {
        return Challenge(
            questId = request.questId,
            customerId = request.customerId, //TODO : 추후 제거
            receiptImage = request.receiptImage,
        )
    }
}