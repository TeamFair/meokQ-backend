package com.meokq.api.user.service

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.user.model.Boss
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
internal class BossServiceTest {

    @Autowired
    private lateinit var service: BossService

    /**
     * Sample Data
     */
    private val sampleAuthReq = AuthReq(
        userId = "BS10000001",
        userType = UserType.BOSS,
    )

    private val sampleModel = Boss(
        bossId = "BS10000001",
        email = "user1@example.com",
    )

    /**
     * call function
     */
    @Test
    fun findByEmail() {
        // given
        val userId = sampleModel.bossId
        val email = sampleModel.email!!

        // when
        val resp = service.findByEmail(email)

        // then
        Assertions.assertEquals(userId, resp.bossId)
    }

    @Test
    @Transactional
    fun save() {
        // given
        val req = LoginReq(
            email = UUID.randomUUID().toString(),
            channel = AuthChannel.APPLE,
            accessToken = "accessToken",
            refreshToken = null,
            userType = UserType.CUSTOMER,
        )

        // when
        val model = service.save(req)

        // then
        Assertions.assertEquals(req.email, model.email)
    }
}