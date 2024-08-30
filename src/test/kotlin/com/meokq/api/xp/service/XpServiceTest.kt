package com.meokq.api.xp.service

import com.meokq.api.TestData.saveCustomer
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.model.XpType
import com.meokq.api.xp.processor.UserAction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class XpServiceTest {

    @Autowired
    private lateinit var service: XpService
    @Autowired
    private lateinit var customerService: CustomerService

    private lateinit var testUser : Customer

    @BeforeEach
    fun setUp() {
        testUser = saveCustomer(customerService)

    }

    @Test
    @DisplayName("등록된 xp가 조회 된다.")
    fun totalXp() {
        val result = service.saveModel(Xp(xpType = XpType.STRENGTH, xpPoint = 50, customer = testUser))

        val customer = customerService.findModelById(result.customer?.customerId!!)

        Assertions.assertEquals(50, customer.totalXp())
    }

    @Test
    @DisplayName("챌린지를 등록하면, 등록할때 기록된 경험치가 증가 되어야 한다.")
    fun gainXp1() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId = testUser.customerId!!)
        service.gain(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,120), challengeMetadata)

        customerService.findModelById(testUser.customerId!!).let {
            Assertions.assertEquals(120, it.totalXp())
        }

    }

    @Test
    @DisplayName("좋아요 이모지를 등록하면 10(사회성) 의 경험치가 증가 되어야 한다.")
    fun gainXp2() {
        val likeMetadata = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId =testUser.customerId!!)
        service.gain(UserAction.LIKE, likeMetadata)

        val customer = customerService.findModelById(testUser.customerId!!)

        Assertions.assertEquals(XpType.SOCIABILITY, customer.xp.get(0).xpType)
        Assertions.assertEquals(10, customer.totalXp())
    }


    @Test
    @DisplayName("챌린지를 삭제하면, 등록할때 기록된 경험치가 감소 되어야 한다.")
    fun withdrawHistory() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId =testUser.customerId!!)
        service.gain(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,50),challengeMetadata)

        service.withdraw(UserAction.CHALLENGE_REPORTED.xpCustomer(XpType.STRENGTH,50),challengeMetadata)
        val customer = customerService.findModelById(testUser.customerId!!)

        Assertions.assertEquals(0, customer.totalXp())
    }

    @Test
    @DisplayName("스텟을 조회하면 각 타입의 스텟이 조회 되어야 한다.")
    fun fetchStats() {
        val challengeMetadata = TargetMetadata(targetId = "challengeTest01", targetType = TargetType.CHALLENGE, userId =testUser.customerId!!)
        service.gain(UserAction.CHALLENGE_REGISTER.xpCustomer(XpType.STRENGTH,50),challengeMetadata)
        val emojiMetadata = TargetMetadata(targetId = "emojiTest01", targetType = TargetType.EMOJI, userId = testUser.customerId!!)
        service.gain(UserAction.LIKE,emojiMetadata)

        val result = service.fetchStats(testUser.customerId!!)

        Assertions.assertEquals(50, result.strength_stat)
        Assertions.assertEquals(10, result.sociability_stat)
        Assertions.assertEquals(0, result.fun_stat)
    }

}