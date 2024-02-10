package com.meokq.api.user.service

import com.meokq.api.TestData.bossBS10000001
import com.meokq.api.TestData.loginReqBossForSave
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class BossServiceTest {

    @Autowired
    private lateinit var service: BossService

    /**
     * call function
     */
    @Test
    @Transactional
    fun findByEmail() {
        // given
        val boss = bossBS10000001
        val userId = boss.bossId
        val email = boss.email!!

        // when
        val resp = service.findByEmail(email)

        // then
        Assertions.assertEquals(userId, resp.userId)
    }

    @Test
    @Transactional
    fun registerMember() {
        // given
        val req = loginReqBossForSave

        // when
        val result = service.registerMember(req)

        // then
        requireNotNull(result.userId)
        val model = service.findModelById(result.userId!!)
        Assertions.assertEquals(req.email, model.email)
    }
}