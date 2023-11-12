package com.meokq.api.application.converter

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.request.ChallengeReq
import com.meokq.api.application.response.ChallengeResp
import org.springframework.stereotype.Component

@Component
class ChallengeConverter : BaseConverter<ChallengeReq, ChallengeResp, Challenge> {
    override fun modelToResponse(model: Challenge): ChallengeResp {
        return ChallengeResp(
            challengeId = model.challengeId,
            receiptImage = model.receiptImage,
            quest = null,
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