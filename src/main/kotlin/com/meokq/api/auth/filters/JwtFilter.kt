package com.meokq.api.auth.filters

import com.meokq.api.auth.enums.ResourceType
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.service.JwtTokenService
import com.meokq.api.core.exception.AccessDeniedException
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
import java.security.SignatureException

@Component
class JwtFilter(
    private val jwtTokenService: JwtTokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // check token
        try {
            val resourceType = ResourceType.getResourceType(request.requestURI)
            val requestUri = request.requestURI
            if (requestUri.startsWith("/h2-console")) {
                filterChain.doFilter(request, response) // 헤더 검사 건너뛰기
                return
            }
            if (resourceType.needToken){
                /**
                 * check token and set security-context
                 */
                val authReq = extractAuthRequest(request)
                setSecurityContext(authReq)

                /**
                 * check diff token with url
                 * 클라이언트가 알맞은 엔드포인트를 요청했는지 확인해야 함.
                 * 예를 들어서, boss 타입의 사용자는 /api/boss 로 시작하는 엔드포인트로만 접근할 수 있음.
                 */
                if (!request.requestURI.startsWith(authReq.userType.requestMapper))
                    throw AccessDeniedException("""
                        ${authReq.userType}타입의 사용자에게 허용되지 않은 리소스입니다. 
                    """.trimIndent())
            } else if (resourceType.needAuthReq) {
                setSecurityContext(AuthReq(userType = UserType.UNKNOWN))
            }

        } catch (e: IllegalArgumentException) { // Token is required
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Token is required")
            return
        } catch (e : MalformedJwtException){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: Invalid token")
            return
        } catch (e : SignatureException){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: ${e.message}")
            return
        } catch (e : AccessDeniedException){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: ${e.message}")
            return
        } catch (e: Exception) {
            // Handle other exceptions as needed
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error")
            return
        }

        filterChain.doFilter(request, response)
    }

    fun setSecurityContext(authReq: AuthReq) {
        val authentication = UsernamePasswordAuthenticationToken(
            authReq,
            null,
            getAuthorities(authReq)
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun extractAuthRequest(request: HttpServletRequest) : AuthReq {
        val token = request.getHeader("authorization")
            ?: throw IllegalArgumentException("Token is required")
        return jwtTokenService.convertToRequest(token)
    }

    fun getAuthorities(authReq : AuthReq): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(authReq.userType.authorization))
    }
}