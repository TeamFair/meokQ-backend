package com.meokq.api.core.controller

import com.meokq.api.auth.enums.ResourceType
import jakarta.servlet.http.HttpServletRequest

interface AuthPrincipal {
    fun needAuthCheck(httpServletRequest: HttpServletRequest) : Boolean {
        val type = ResourceType.getResourceType(httpServletRequest.requestURI)
        if (type.needToken) return true
        return false
    }
}