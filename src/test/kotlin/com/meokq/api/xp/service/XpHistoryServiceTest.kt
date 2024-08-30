package com.meokq.api.xp.service

import com.meokq.api.TestData.saveCustomer
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.dto.request.XpSearchDto
import com.meokq.api.xp.model.XpType
import com.meokq.api.xp.processor.UserAction
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
internal class XpHistoryServiceTest {

    @Autowired
    private lateinit var service: XpHistoryService
    @Autowired
    private lateinit var customerService: CustomerService

    private lateinit var testUser : Customer

    @BeforeEach
    fun setUp() {
        testUser = saveCustomer(customerService)
    }

    @Test
    @DisplayName("회원의 경험치 로그가 조회 되어야 한다.")
    fun findAll() {
        service.writeHistory(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,50), testUser.customerId!!)
        service.writeHistory(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.FUN,50), testUser.customerId!!)
        service.writeHistory(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.CHARM,50), testUser.customerId!!)

        val searchDto = XpSearchDto(userId = testUser.customerId!!)
        val pageable = PageRequest.of(0, 10)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(3, xpHisResps.totalElements)
    }

    @Test
    @DisplayName("챌린지를 삭제하면, 감소된 히스토리가 남아 있어야 한다..")
    fun writeLog1() {
        val registerUserAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.CHARM,50)
        val deleteUserAction = UserAction.CHALLENGE_DELETE.xpCustomer(XpType.CHARM,50)
        service.writeHistory(registerUserAction, testUser.customerId!!)
        service.withdrawHistory(deleteUserAction, testUser.customerId!!)

        val pageable = PageRequest.of(0, 10)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(2, xpHisResps.totalElements)
        Assertions.assertEquals(-50, xpHisResps.content[0].xpPoint)
    }


}