package com.meokq.api.xp.processor.impl

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.user.request.CustomerXpReq
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.processor.XpProcessor
import org.springframework.stereotype.Component

@Component
class EmojiXpProcessorImpl: XpProcessor, AuthDataProvider {
    override fun isTarget(): Boolean {
        val authReq = getAuthReq()
        if (authReq.userType != UserType.CUSTOMER) return false

        // TODO : 좋아요를 누른 이력이 있는지 확인
        return true
    }

    override fun getXpReq(): CustomerXpReq {
        return CustomerXpReq(UserAction.LIKE)
    }
}