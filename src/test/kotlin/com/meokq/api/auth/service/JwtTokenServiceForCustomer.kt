package com.meokq.api.auth.service

import com.meokq.api.TestData.authReqCS10000001
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("Customer 타입의 사용자의 tokenService 검증")
internal class JwtTokenServiceForCustomer: JwtTokenServiceTestForUser {

    @Autowired
    private lateinit var service: JwtTokenService
    override fun getTokenService() = service

    /**
     * create Token
     */
    @Test
    @DisplayName("토큰을 생성한다.")
    fun generateTokenForCustomer() {
        generateToken(authReqCS10000001)
    }


    /**
     * validate Token
     */

    @Test
    @DisplayName("유효한 토큰을 검증한다.")
    fun validateTokenForCustomer() {
        validateToken(authReqCS10000001)
    }

    @Test
    @DisplayName("조작된 토큰을 검증한다.")
    fun invalidateTokenForCustomer() {
        invalidateToken(authReqCS10000001)
    }

    /**
     * convertToRequest
     */

    @Test
    @DisplayName("유효한 토큰에서 사용자 정보를 추출한다.")
    fun convertValidateTokenToRequestForCustomer() {
        convertValidateTokenToRequest(
            authReqForToken = authReqCS10000001,
            userId = "CS10000001"
        )
    }

    @Test
    @DisplayName("조작된 토큰에서 사용자 정보를 추출한다.")
    fun convertInvalidateTokenToRequestForCustomer() {
        convertInvalidateTokenToRequest(
            authReqForToken = authReqCS10000001,
        )
    }

}