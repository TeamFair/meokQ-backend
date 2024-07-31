package com.meokq.api.xp

import com.meokq.api.TestData
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
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
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class XpHisServiceTest {

    @Autowired
    private lateinit var service: XpHisService
    @Autowired
    private lateinit var customerService: CustomerService

    private lateinit var testCustomer: Customer

    @BeforeEach
    fun setUp() {
        testCustomer = TestData.saveCustomer(customerService)
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T02", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T03", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
    }

    @Test
    @DisplayName("경험치 로그를 조회한다.")
    fun findAll() {
        val searchDto = XpSearchDto(userId = testCustomer.customerId, title = "T01")
        val pageable = PageRequest.of(0, 2)
        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(2, xpHisResps.size)
        Assertions.assertEquals(3, xpHisResps.totalElements)
    }
}