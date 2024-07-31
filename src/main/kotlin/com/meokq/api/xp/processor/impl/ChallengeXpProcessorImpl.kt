package com.meokq.api.xp.processor.impl

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.processor.XpProcessor
import org.springframework.stereotype.Component

@Component
class ChallengeXpProcessorImpl : XpProcessor {
    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(
            userAction = UserAction.CHALLENGE_REGISTER
        )
    }
}