package com.meokq.api.application.repository

import com.meokq.api.user.model.Boss
import com.meokq.api.user.repository.BossRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
@ActiveProfiles("local")
internal class BossRepositoryTest{

    @Autowired
    lateinit var repository: BossRepository

    private val boss = Boss(
        bossId = "BS10000001",
        email = "user1@example.com"
    )

    @Test
    fun getBossByEmail() {
        // when
        val result = repository.findByEmail(boss.email!!)

        // then
        Assertions.assertEquals(boss.email, result?.email)
        Assertions.assertEquals(boss.bossId, result?.bossId)
    }

    @Test
    @Transactional
    fun save() {
        val result = repository.save(boss)
        Assertions.assertSame(boss.email, result.email)
        Assertions.assertNotNull(result.bossId)
    }
}