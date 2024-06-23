package com.meokq.api.logs

import com.meokq.api.TestData
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.logs.dto.XpSearchDto
import com.meokq.api.logs.model.XpHistory
import com.meokq.api.logs.service.XpHisService
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
        service.saveModel(XpHistory(description = "test1", title = "T01", xpPoint = 100, userId = testCustomer.customerId))
        service.saveModel(XpHistory(description = "test2", title = "T01", xpPoint = 100, userId = testCustomer.customerId))
        service.saveModel(XpHistory(description = "test3", title = "T01", xpPoint = 100, userId = testCustomer.customerId))
        service.saveModel(XpHistory(description = "test4", title = "T02", xpPoint = 100, userId = testCustomer.customerId))
        service.saveModel(XpHistory(description = "test5", title = "T03", xpPoint = 100, userId = testCustomer.customerId))
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