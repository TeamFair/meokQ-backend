package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test

internal class JwtTokenServiceTest {
    private val secret = "secretKeyMeakqsecretKeyMeakqtemp"
    private val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    @Test
    fun generateTokenForCustomer() {

        val result = Jwts.builder()
            .claim("userId", "CS10000001")
            .claim("userType", UserType.CUSTOMER)
            .claim("accessToken", null)
            .claim("refreshToken", null)
            //.claim("email", request.email)
            .claim("channel", null)
            //.setIssuedAt(Date())
            //.setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey)
            .compact()

        print(result)
    }


    @Test
    fun generateTokenForBoss() {

        val result = Jwts.builder()
            .claim("userId", "BS10000001")
            .claim("userType", UserType.BOSS)
            .claim("accessToken", null)
            .claim("refreshToken", null)
            //.claim("email", request.email)
            .claim("channel", null)
            //.setIssuedAt(Date())
            //.setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey)
            .compact()

        print(result)
    }
}
