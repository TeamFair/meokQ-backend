package com.meokq.api.auth.service

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
internal class JwtTokenServiceTest {
    private val secret = "secretKeyMeakqsecretKeyMeakqtemp"
    private val secretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    @Autowired
    private lateinit var service: JwtTokenService

    @Test
    @DisplayName("jwt형식이 아닌 토큰을 검증한다.")
    fun invalidateToken() {
        // given
        val tokenValue = UUID.randomUUID().toString()

        // when
        Assertions.assertThrows(JwtException::class.java) {
            val result = service.validateToken(tokenValue)
        }
    }

    @Test
    @DisplayName("jwt형식이 아닌 토큰에서 사용자 정보를 추출한다.")
    fun convertInvalidateTokenToRequest() {
        // given
        val token = UUID.randomUUID().toString()

        // when
        Assertions.assertThrows(JwtException::class.java) {
            val result = service.convertToRequest(token)
        }
    }
}
