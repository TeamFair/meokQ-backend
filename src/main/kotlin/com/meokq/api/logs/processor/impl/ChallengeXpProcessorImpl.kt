package com.meokq.api.logs.processor.impl

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.logs.processor.UserAction
import com.meokq.api.logs.processor.XpProcessor
import org.springframework.stereotype.Component

@Component
class ChallengeXpProcessorImpl : XpProcessor {
    override fun isTarget(): Boolean {
        return true
    }

    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(
            userAction = UserAction.CHALLENGE_REGISTER
        )
    }
}