package com.meokq.api.xp

import com.meokq.api.xp.dto.XpSearchDto
import com.meokq.api.xp.model.XpHistory
import com.meokq.api.xp.service.XpHisService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
internal class XpHisServiceTest {

    @Autowired
    private lateinit var service: XpHisService

    @BeforeEach
    fun setUp() {
        service.saveModel(XpHistory(description = "test1", title = "T01", xpPoint = 100, userId = "user01"))
        service.saveModel(XpHistory(description = "test2", title = "T01", xpPoint = 100, userId = "user01"))
        service.saveModel(XpHistory(description = "test3", title = "T01", xpPoint = 100, userId = "user01"))
        service.saveModel(XpHistory(description = "test4", title = "T02", xpPoint = 100, userId = "user01"))
        service.saveModel(XpHistory(description = "test5", title = "T03", xpPoint = 100, userId = "user02"))
    }

    @Test
    @DisplayName("경험치 로그를 조회한다.")
    fun findAll() {
        val searchDto = XpSearchDto(userId = "user01", title = "T01")
        val pageable = PageRequest.of(0, 2)
        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(2, xpHisResps.size)
        Assertions.assertEquals(3, xpHisResps.totalElements)

        val content = xpHisResps.content.toList()
        val firstOne = content.first()
        Assertions.assertEquals(3, firstOne.recordId)
    }
}