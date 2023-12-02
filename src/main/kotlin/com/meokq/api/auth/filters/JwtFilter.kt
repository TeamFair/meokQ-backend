package com.meokq.api.auth.filters

import com.meokq.api.auth.service.JwtTokenService
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtTokenService: JwtTokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val requestUri = request.requestURI

        try {
            if (requestUri.startsWith("/api/")) authenticateRequest(request)
        } catch (e: IllegalArgumentException) {
            // Token is required
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Token is required")
            return
        } catch (e : MalformedJwtException){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Invalid token")
            return
        } catch (e: Exception) {
            // Handle other exceptions as needed
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error")
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun authenticateRequest(request: HttpServletRequest) {
        val token = request.getHeader("authorization") ?: throw IllegalArgumentException("Token is required")
        if (jwtTokenService.validateToken(token)) {
            val authentication = UsernamePasswordAuthenticationToken(
                token,
                null,
                null
            )

            authentication.details = jwtTokenService.convertToRequest(token)
            SecurityContextHolder.getContext().authentication = authentication
        } else {
            // Token validation failed
            throw IllegalArgumentException("Invalid token")
        }
    }
}