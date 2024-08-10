package com.meokq.api.xp

import com.meokq.api.TestData
import com.meokq.api.TestData.authReqCS10000001
import com.meokq.api.TestData.loginReqCS10000001
import com.meokq.api.TestData.saveCustomer
import com.meokq.api.TestData.saveLoginReqCS10000001
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.dto.XpSearchDto
import com.meokq.api.xp.model.XpHistory
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHistoryService
import org.apache.catalina.User
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
        testUser = saveLoginReqCS10000001(customerService)
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = testUser.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = "testUser2")))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest01",targetType = TargetType.CHALLENGE,userId = "testUser3")))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest02",targetType = TargetType.CHALLENGE,userId = testUser.customerId!!)))
        service.saveModel(XpHistory(title = "T01", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest03",targetType = TargetType.CHALLENGE,userId = testUser.customerId!!)))
        service.saveModel(XpHistory(title = "T02", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest04",targetType = TargetType.CHALLENGE,userId = testUser.customerId!!)))
        service.saveModel(XpHistory(title = "T03", xpPoint = 100, targetMetadata = TargetMetadata(targetId = "challengeTest05",targetType = TargetType.CHALLENGE,userId = testUser.customerId!!)))
        service.saveModel(XpHistory(title = "E01", xpPoint = 10, targetMetadata = TargetMetadata(targetId = "emojiTest01",targetType = TargetType.EMOJI,userId = testUser.customerId!!)))

    }

    @Test
    @DisplayName("경험치 로그를 조회한다.")
    fun findAll() {
        val searchDto = XpSearchDto(userId = testUser.customerId!!, title = "T01")
        val pageable = PageRequest.of(0, 10)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(3, xpHisResps.totalElements)
    }

    @Test
    @DisplayName("챌린지를 등록하면, 등록할때 기록된 경험치가 증가 되어야 한다.")
    fun increaseXp1() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId ="challengeCreator")
        service.save(UserAction.CHALLENGE_REGISTER.xpCustomer(20), challengeMetadata)
        val pageable = PageRequest.of(0, 10)
        val searchDto = XpSearchDto(userId = "challengeCreator")

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(1, xpHisResps.totalElements)
        Assertions.assertEquals(20, xpHisResps.content[0].xpPoint)
    }

    @Test
    @DisplayName("좋아요 이모지를 등록하면 10의 경험치가 증가 되어야 한다.")
    fun increaseXp2() {
        val likeMetadata = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId =testUser.customerId!!)
        service.save(UserAction.LIKE, likeMetadata)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)
        val pageable = PageRequest.of(0, 10)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(7, xpHisResps.totalElements)
    }

    @Test
    @DisplayName("챌린지를 삭제하면, 등록할때 기록된 경험치가 감소 되어야 한다.")
    fun decreaseXp1() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId =testUser.customerId!!)
        service.deleteByTargetMetadata(challengeMetadata)
        val pageable = PageRequest.of(0, 10)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(5, xpHisResps.totalElements)
    }

    @Test
    @DisplayName("이모지를 삭제하면 10의 경험치가 감소 되어야 한다.")
    fun decreaseXp2() {
        val likeMetadata = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId = testUser.customerId!!)
        service.deleteByTargetMetadata(likeMetadata)
        val pageable = PageRequest.of(0, 2)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)

        val xpHisResps = service.findAll(searchDto, pageable)
        val customerInfo = customerService.findModelById(testUser.customerId!!)

        Assertions.assertEquals(5, xpHisResps.totalElements)
    }


}