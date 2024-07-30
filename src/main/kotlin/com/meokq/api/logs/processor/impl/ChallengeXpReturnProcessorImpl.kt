package com.meokq.api.logs.processor.impl

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.logs.processor.UserAction
import com.meokq.api.logs.processor.XpProcessor
import com.meokq.api.logs.processor.XpReturnProcessor
import org.springframework.stereotype.Component

@Component
class ChallengeXpReturnProcessorImpl : XpReturnProcessor {

    override fun returnXpReq(): CustomerXpReq {
        return CustomerXpReq(
            userAction = UserAction.CHALLENGE_DELETE
        )
    }
}