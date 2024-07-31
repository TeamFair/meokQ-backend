package com.meokq.api.xp.processor.impl

import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.processor.XpProcessor
import org.springframework.stereotype.Component

@Component
class EmojiXpProcessorImpl: XpProcessor{
    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(UserAction.LIKE)
    }
}