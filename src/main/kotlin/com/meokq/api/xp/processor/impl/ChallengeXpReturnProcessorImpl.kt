package com.meokq.api.xp.processor.impl

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.processor.XpReturnProcessor
import org.springframework.stereotype.Component

@Component
class ChallengeXpReturnProcessorImpl : XpReturnProcessor {

    override fun returnXpReq(): CustomerXpReq {
        return CustomerXpReq(
            userAction = UserAction.CHALLENGE_DELETE
        )
    }
}