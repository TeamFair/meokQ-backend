package com.meokq.api.redis

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class RedisTokenServiceImplTest {

    @Autowired
    private lateinit var redisTokenService: RedisTokenService

    @Test
    fun `save and get token`() {
        val userId = "testUser"
        val token = "testToken"

        redisTokenService.saveToken(userId, token)
        val retrievedToken = redisTokenService.getToken(userId)

        assertNotNull(retrievedToken)
        assertEquals(token, retrievedToken)
    }

    @Test
    fun `delete token`() {
        val userId = "testUser"
        val token = "testToken"

        redisTokenService.saveToken(userId, token)
        redisTokenService.deleteToken(userId)

        val retrievedToken = redisTokenService.getToken(userId)
        assertNull(retrievedToken)
    }

    @Test
    fun `delete all tokens`() {
        redisTokenService.saveToken("user1", "token1")
        redisTokenService.saveToken("user2", "token2")

        redisTokenService.deleteAllTokens()

        assertNull(redisTokenService.getToken("user1"))
        assertNull(redisTokenService.getToken("user2"))
    }
}