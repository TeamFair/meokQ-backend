package com.meokq.api.core.controller

import com.meokq.api.auth.request.AuthReq

interface CheckAuthCallback {
    fun <SH>beforeDelete(
        authReq: AuthReq,
        req: SH? = null,
        param : Any? = null
    ){}
    fun <SV>beforeSave(
        authReq: AuthReq,
        req: SV? = null,
        param : Any? = null
    ){}
    fun <SV, SH>beforeUpdate(
        authReq: AuthReq,
        saveReq: SV? = null,
        searchReq: SH? = null,
        param : Class<*>? = null
    ){}
    fun <SH>beforeSelect(
        authReq: AuthReq,
        req: SH? = null,
        param : Any? = null
    ){}
}