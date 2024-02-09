package com.meokq.api.notice.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.request.NoticeSearchDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
internal class NoticeServiceTest {
    @Autowired
    private lateinit var service: NoticeService

    /**
     * Sample Data
     */
    private val sampleSearchDto = NoticeSearchDto(
        target = NoticeTarget.CUSTOMER
    )

    private val sampleAuthReq = AuthReq(
        userType = UserType.CUSTOMER,
        userId = "sample-id"
    )

    private val samplePageable = PageRequest.of(0, 10)

    /**
     * call function
     */
    @Test
    fun findAll() {
        // given
        // when
        val result = service.findAll(
            searchDto = sampleSearchDto,
            authReq = sampleAuthReq,
            pageable = samplePageable,
        )

        // then
        Assertions.assertTrue(!result.isEmpty)
        result.forEach {
            Assertions.assertEquals(sampleSearchDto.target, it.target)
        }
    }
}