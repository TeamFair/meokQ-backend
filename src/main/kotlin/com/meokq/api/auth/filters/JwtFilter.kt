package com.meokq.api.auth.filters

import com.meokq.api.auth.enums.ResourceType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.service.JwtTokenService
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
        try {
            val resourceType = ResourceType.getResourceType(request.requestURI)
            if (resourceType.isAuthTarget){
                authenticateRequest(request)
            }

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

    private fun authenticateRequest(
        request: HttpServletRequest,
    ) {
        val authReq = extractAuthRequest(request)

        // SecurityContext에 authReq 저장
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            authReq,
            null,
            getAuthorities(authReq)
        )
    }

    private fun extractAuthRequest(request: HttpServletRequest) : AuthReq {
        val token = request.getHeader("authorization")
            ?: throw IllegalArgumentException("Token is required")
        return jwtTokenService.convertToRequest(token)
    }

    fun getAuthorities(authReq : AuthReq): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(authReq.userType.name))
    }
}