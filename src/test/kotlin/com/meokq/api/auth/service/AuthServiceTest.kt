package com.meokq.api.auth.service

import com.meokq.api.TestData.loginReqCustomerForSave
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class AuthServiceTest{

    @Autowired
    private lateinit var service: AuthService
    @Autowired
    private lateinit var jwtTokenService: JwtTokenService

    /**
     * call login
     */
    @Test
    @Transactional
    @DisplayName("신규 사용자가 로그인을 요청하면, 회원가입후 토큰을 반환합니다.")
    fun registerCustomer() {
        // given
        val loginReq = loginReqCustomerForSave

        /*// when
        val result = service.login(loginReq)

        // then
        Assertions.assertNotNull(result.authorization)
        val authReq = jwtTokenService.convertToRequest(result.authorization!!)
        Assertions.assertNotNull(authReq.userId)
        Assertions.assertEquals(authReq.userType, loginReq.userType)*/
    }

    @Test
    @Transactional
    @DisplayName("신규 사용자가 로그인을 요청하면, 회원가입후 토큰을 반환합니다.")
    fun loginCustomer() {
        // given
        // when
        val result = service.login(loginReqCustomerForSave)

        // then

    }

    /*@Test
    @Transactional
    @DisplayName("신규 사용자가 로그인을 요청하면, 회원가입후 토큰을 반환합니다.")
    fun registerCustomer() {
        // given
        val req = loginReqCustomerForSave

        // when
        val result = service.login(req)

        // then
        Assertions.assertNotNull(result.authorization)
    }*/

    @Test
    fun withdraw() {
        // given
        // when
        // then
    }
}