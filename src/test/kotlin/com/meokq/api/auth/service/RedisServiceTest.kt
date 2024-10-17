package com.meokq.api.auth.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Duration
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
internal class RedisServiceTest(
    @Autowired
    private val redisService : RedisService
) {
    var uuidKey : String = ""

    @BeforeEach
    fun generateKey(){
        uuidKey = "testKey-${UUID.randomUUID()}"
    }

    @AfterEach
    fun deleteKey(){
        if (uuidKey != ""){
            redisService.deleteByKey(uuidKey)
        }
    }

    @Test
    @DisplayName("레디스에 값을 저장하고 삭제합니다.")
    fun saveString() {
        val key = uuidKey
        val value = "my-value"
        val duration = Duration.ofMinutes(1)

        redisService.save(key, value, duration)
        val findValue = redisService.findByKey(key)
        val result = redisService.deleteByKey(key)
        assertEquals(value, findValue)
        assertEquals(true, result)
    }

    @Test
    @DisplayName("레디스에 값을 저장하고, 유효기간이 만료된 후에는 삭제되어야 합니다.")
    fun durationSave() {
        val key = uuidKey
        val value = "my-value2"
        val second : Long = 5
        val duration = Duration.ofSeconds(second)

        redisService.save(key, value, duration)
        val findValueBeforeExpiration = redisService.findByKey(key)

        Thread.sleep(second*1000)
        val findValueAfterExpiration = redisService.findByKey(key)
        val result = redisService.deleteByKey(uuidKey)

        assertEquals(value, findValueBeforeExpiration)
        assertEquals(null, findValueAfterExpiration)
        assertEquals(false, result)
    }
}