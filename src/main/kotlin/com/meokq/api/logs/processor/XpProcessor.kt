package com.meokq.api.logs.processor

import com.meokq.api.user.request.CustomerXpReq

interface XpProcessor {
    fun getXpReq() : CustomerXpReq
}