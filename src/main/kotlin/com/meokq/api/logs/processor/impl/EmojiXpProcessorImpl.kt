package com.meokq.api.logs.processor.impl

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.logs.processor.UserAction
import com.meokq.api.logs.processor.XpProcessor
import com.meokq.api.logs.processor.XpReturnProcessor
import org.springframework.stereotype.Component

@Component
class EmojiXpProcessorImpl: XpProcessor{
    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(UserAction.LIKE)
    }
}