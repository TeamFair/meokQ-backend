package com.meokq.api.user.service

import com.meokq.api.core.exception.InvalidRequestException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
internal class AdminServiceTest {

    @Value("\${matq.admin.email:admin}")
    private lateinit var adminEmail: String

    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Autowired
    private lateinit var service: AdminService

    @Test
    @DisplayName("특정 계정으로 접근하면, 사용자 정보를 반환합니다.")
    fun findByEmail() {
        // given
        // when
        val result = service.findByEmail(adminEmail)

        // then
        Assertions.assertEquals(adminId, result.userId)
    }

    @Test
    @DisplayName("잘못된 계정정보로 접근하면, 오류가 발생합니다.")
    fun findByEmailException() {
        // given
        val adminEmail2 = UUID.randomUUID().toString()

        Assertions.assertThrows(InvalidRequestException::class.java) {
            service.findByEmail(adminEmail2)
        }
    }
}