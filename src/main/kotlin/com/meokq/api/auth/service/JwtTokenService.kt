package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.RefreshToken
import com.meokq.api.core.exception.TokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
class JwtTokenService(
    @Value("\${jwt.secret-key:null}")
    private val secret: String,
    @Value("\${spring.profiles.active:local}")
    private val profiles: String,
) {
    private val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val ttlAccess = 864_000_000 // 10 days
    private val ttlRefresh = Duration.ofDays(1)

    fun generateToken(request : AuthReq): String {
        return Jwts.builder()
            .claim("userId", request.userId)
            .claim("userType", request.userType)
            //.claim("accessToken", request.accessToken)
            //.claim("refreshToken", request.refreshToken)
            //.claim("email", request.email)
            //.claim("channel", request.channel)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ttlAccess))
            .signWith(secretKey)
            .compact()
    }

    fun generateRefreshToken(request: AuthReq): RefreshToken {
        return RefreshToken(
            profile = profiles,
            userId = request.userId,
            data = UUID.randomUUID().toString(),
            ttl = ttlRefresh,
        )
    }

    fun validateToken(token : String) : Boolean {
        try {
            val claims = extractClaims(token)
            // TODO: Validate token based on service requirements
            return true
        } catch (e : MalformedJwtException){
            throw TokenException("토큰이 유효하지 않습니다.")
        } catch (e: SignatureException){
            throw TokenException("토큰이 유효하지 않습니다")
        } catch (e : Exception){
            throw e
        }
    }

    private fun extractClaims(token: String) : Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun convertToRequest(token: String) : AuthReq {
        val claims = extractClaims(token)

        return AuthReq(
            userType = UserType.fromString(
                claims.get("userType", String::class.java)
            ),
            userId = claims.get("userId", String::class.java)
        )
    }
}