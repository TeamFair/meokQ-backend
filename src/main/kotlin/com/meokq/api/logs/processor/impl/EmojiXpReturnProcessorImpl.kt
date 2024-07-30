package com.meokq.api.logs.processor.impl

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.logs.processor.UserAction
import com.meokq.api.logs.processor.XpProcessor
import org.springframework.stereotype.Component

@Component
class EmojiXpReturnProcessorImpl: XpProcessor{
    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(UserAction.UN_LIKE)
    }
}