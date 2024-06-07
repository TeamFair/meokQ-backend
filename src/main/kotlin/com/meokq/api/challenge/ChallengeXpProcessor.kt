package com.meokq.api.challenge

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.xp.UserAction
import com.meokq.api.xp.XpProcessor
import org.springframework.stereotype.Component

@Component
class ChallengeXpProcessor : XpProcessor {
    override fun isTarget(): Boolean {
        return true
    }

    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(
            userAction = UserAction.CHALLENGE_REGISTER
        )
    }
}