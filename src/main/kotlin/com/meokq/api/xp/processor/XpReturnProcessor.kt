package com.meokq.api.xp.processor

import com.meokq.api.user.request.CustomerXpReq

interface XpReturnProcessor {
    fun returnXpReq() : CustomerXpReq
}