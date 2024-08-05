package com.meokq.api.xp

import com.meokq.api.TestData
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.dto.XpHisResp
import com.meokq.api.xp.dto.XpSearchDto
import com.meokq.api.xp.model.XpHistory
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHisService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
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

    private lateinit var challengeCreator : TargetMetadata
    private lateinit var likeCreator : TargetMetadata

    @BeforeEach
    fun setUp() {
        testCustomer = TestData.saveCustomer(customerService)
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T02", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "T03", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))
        service.saveModel(XpHistory(title = "E01", xpPoint = 10, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testCustomer.customerId!!)))

        challengeCreator = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId = "challengeCreator")
        likeCreator = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId = "emojiCreator")
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

    @Test
    @DisplayName("챌린지를 등록하면, 등록할때 기록된 경험치가 저장 되어야 한다.")
    fun save() {
        service.save(UserAction.CHALLENGE_REGISTER.xpCustomer(20), challengeCreator)
        val pageable = PageRequest.of(0, 2)
        val searchDto = XpSearchDto(userId = "challengeCreator")
        val xpHisResps = service.findAll(searchDto, pageable)

        println(xpHisResps)
        println(xpHisResps.content[0].xpPoint)

        Assertions.assertEquals(1, xpHisResps.totalElements)
        Assertions.assertEquals(20, xpHisResps.content[0].xpPoint)
    }

    @Test
    @DisplayName("좋아요 이모지를 등록하면 10의 경험치가 저장 되어야 한다.")
    fun save1() {
        val searchDto = XpSearchDto(userId = "emojiCreator")
        val pageable = PageRequest.of(0, 2)
        service.save(UserAction.LIKE, likeCreator)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(1, xpHisResps.totalElements)
        Assertions.assertEquals(10, xpHisResps.content.get(0).xpPoint)
    }

}