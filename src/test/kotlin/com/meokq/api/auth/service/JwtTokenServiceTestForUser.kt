package com.meokq.api.auth.service

import com.meokq.api.auth.request.AuthReq
import io.jsonwebtoken.JwtException
import org.junit.jupiter.api.Assertions
import org.springframework.transaction.annotation.Transactional

interface JwtTokenServiceTestForUser {

    /**
     * create Token
     */
    @Transactional
    fun generateToken(authReqForToken: AuthReq){
        // given
        val tokenService = getTokenService()

        // when
        val token = tokenService.generateToken(authReqForToken)
        print(token)
    }

    /**
     * validate Token
     */
    @Transactional
    fun validateToken(authReqForToken: AuthReq) {
        // given
        val tokenService = getTokenService()
        val token = tokenService.generateToken(authReqForToken)

        // when
        var result = false
        Assertions.assertDoesNotThrow {
            result = tokenService.validateToken(token)
        }

        // then
        Assertions.assertEquals(true, result)
    }

    @Transactional
    fun invalidateToken(authReqForToken: AuthReq) {
        // given
        val tokenService = getTokenService()
        val token = tokenService.generateToken(authReqForToken)
        val newToken = "${token}test"

        // when
        Assertions.assertThrows(JwtException::class.java) {
            val result = tokenService.validateToken(newToken)
        }
    }

    /**
     * convertToRequest
     */
    @Transactional
    fun convertValidateTokenToRequest(authReqForToken: AuthReq, userId: String){
        // given
        val tokenService = getTokenService()
        val token = tokenService.generateToken(authReqForToken)

        // when
        val result = tokenService.convertToRequest(token)

        // then
        Assertions.assertEquals(authReqForToken.userType, result.userType)
        Assertions.assertEquals(userId, result.userId)
    }

    @Transactional
    fun convertInvalidateTokenToRequest(authReqForToken: AuthReq) {
        // given
        val tokenService = getTokenService()
        val token = tokenService.generateToken(authReqForToken)
        val newToken = "${token}test"

        // when
        Assertions.assertThrows(JwtException::class.java) {
            val result = tokenService.convertToRequest(newToken)
        }
    }

    /**
     * etc
     */
    fun getTokenService(): JwtTokenService

}