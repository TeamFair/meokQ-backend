package com.meokq.api.auth.filters

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.service.AuthService
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.exception.InvalidRequestException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RedisTokenFilter(
    private val authService: AuthService
): OncePerRequestFilter(), AuthDataProvider{

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authReq = getAuthReq()
            if (authReq.userType != UserType.UNKNOWN){
                val token = request.getHeader("authorization")
                    ?: throw InvalidRequestException("Token is required")

                val isTokenValid = authService.isTokenValid(authReq.userId!!, token)
                if (!isTokenValid) throw InvalidRequestException("Invalid or expired token.")
            }

        } catch (e: InvalidRequestException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: ${e.message}")
            return
        }

        filterChain.doFilter(request, response)
    }

}