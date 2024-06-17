package com.meokq.api.user.service

import com.meokq.api.TestData
import com.meokq.api.TestData.loginReqBossForSave
import com.meokq.api.core.exception.NotUniqueException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
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
     * call function
     */
    @Test
    @Transactional
    fun findByEmail() {
        // given
        val boss = TestData.saveBoss(service)
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
        req.userId = UUID.randomUUID().toString()
        req.email = UUID.randomUUID().toString()

        // when
        val result = service.registerMember(req)

        // then
        requireNotNull(result.userId)
        val model = service.findModelById(result.userId!!)
        Assertions.assertEquals(req.email, model.email)
    }

    @Test
    @Transactional
    @DisplayName("이메일이 같은 사용자는 등록할 수 없습니다.")
    fun registerMember2() {
        // given
        val req = loginReqBossForSave
        req.userId = UUID.randomUUID().toString()
        req.email = req.userId+"@example.com"

        // when
        service.registerMember(req)
        Thread.sleep(100)

        Assertions.assertThrows(NotUniqueException::class.java){
            service.registerMember(req)
        }
    }
}