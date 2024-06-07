package com.meokq.api.xp

import com.meokq.api.user.request.CustomerXpReq

interface XpProcessor {
    fun isTarget(): Boolean
    fun getXpReq() : CustomerXpReq
}