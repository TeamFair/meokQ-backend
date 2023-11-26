package com.meokq.api.application.repository

import com.meokq.api.user.model.Boss
import com.meokq.api.user.repository.BossRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
internal class BossRepositoryTest{

    @Autowired
    lateinit var repository: BossRepository

    private val boss = Boss(
        nickName = "sample-boss",
        email = "sample@example.com"
    )

    @Test
    @Transactional
    fun getBossByEmail() {
        save()
        val result = repository.findBossByEmail(boss.email!!)
        Assertions.assertSame(boss.email, result?.email)
        Assertions.assertNotNull(boss.bossId)
        println(result)
    }

    @Test
    @Transactional
    fun save() {
        val result = repository.save(boss)
        Assertions.assertSame(boss.email, result.email)
        Assertions.assertNotNull(boss.bossId)
        println(result)
    }
}