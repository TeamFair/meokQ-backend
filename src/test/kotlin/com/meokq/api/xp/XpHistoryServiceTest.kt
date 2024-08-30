package com.meokq.api.xp

import com.meokq.api.TestData.saveCustomer
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.dto.XpSearchDto
import com.meokq.api.xp.model.XpHistory
import com.meokq.api.xp.model.XpType
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHistoryService
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
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,50), userId = testUser.customerId!!))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,10), userId = testUser.customerId!!))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,200), userId = "testUser1"))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,100), userId = "testUser2"))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,40), userId = testUser.customerId!!))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,20), userId = testUser.customerId!!))
        service.saveModel(XpHistory(userAction = UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,90), userId = "testUser3"))


    }

    @Test
    @DisplayName("경험치 로그를 조회한다.")
    fun findAll() {
        val searchDto = XpSearchDto(userId = testUser.customerId!!, title = "T01")
        val pageable = PageRequest.of(0, 10)

        val xpHisResps = service.findAll(searchDto, pageable)

        Assertions.assertEquals(1, xpHisResps.totalElements)
    }

    @Test
    @DisplayName("챌린지를 등록하면, 등록할때 기록된 경험치가 증가 되어야 한다.")
    fun increaseXp1() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId ="challengeCreator")
        service.writeHistory(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,50), challengeMetadata.userId)
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
        service.writeHistory(UserAction.LIKE, likeMetadata.userId)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)
        val pageable = PageRequest.of(0, 10)

        val xpHisResps = service.findAll(searchDto, pageable)
        val totalXpPoints = xpHisResps.content.sumOf { it.xpPoint }

        Assertions.assertEquals(6, xpHisResps.totalElements)
        Assertions.assertEquals(420, totalXpPoints)
    }


    @Test
    @DisplayName("챌린지를 삭제하면, 등록할때 기록된 경험치가 감소 되어야 한다.")
    fun decreaseXp1() {
//        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId =testUser.customerId!!)
//        service.deleteByTargetMetadata(challengeMetadata)
        val pageable = PageRequest.of(0, 10)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)

        val xpHisResps = service.findAll(searchDto, pageable)
        val totalXpPoints = xpHisResps.content.sumOf { it.xpPoint }

        Assertions.assertEquals(4, xpHisResps.totalElements)
        Assertions.assertEquals(310, totalXpPoints)
    }

    @Test
    @DisplayName("이모지를 삭제하면 10의 경험치가 감소 되어야 한다.")
    fun decreaseXp2() {
//        val likeMetadata = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId = testUser.customerId!!)
//        service.deleteByTargetMetadata(likeMetadata)
        val pageable = PageRequest.of(0, 10)
        val searchDto = XpSearchDto(userId = testUser.customerId!!)

        val xpHisResps = service.findAll(searchDto, pageable)
        val totalXpPoints = xpHisResps.content.sumOf { it.xpPoint }

        Assertions.assertEquals(4, xpHisResps.totalElements)
        Assertions.assertEquals(400, totalXpPoints)

    }


}