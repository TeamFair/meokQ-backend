package com.meokq.api.xp

import com.meokq.api.TestData
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.service.JwtTokenService
import com.meokq.api.auth.service.JwtTokenServiceTestForUser
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.service.QuestService
import com.meokq.api.user.model.Customer
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.dto.XpSearchDto
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHisService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
internal class XpProcessorTest: JwtTokenServiceTestForUser {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired private lateinit var questService: QuestService
    @Autowired private lateinit var challengeService: ChallengeService
    @Autowired private lateinit var xpHisService: XpHisService
    @Autowired private lateinit var customerService: CustomerService
    @Autowired private lateinit var bossService: BossService
    @Autowired private lateinit var marketService: MarketService
    @Autowired private lateinit var service: JwtTokenService
    override fun getTokenService() = service

    lateinit var testCustomer: Customer
    lateinit var testQuest: Quest
    lateinit var testChallenge: Challenge
    lateinit var customerToken: String
    lateinit var customerAuthReq: AuthReq

    fun saveTargetChallenge(){
        // customer
        testCustomer = TestData.saveCustomer(customerService).copy()
        customerAuthReq = AuthReq(
            userId = testCustomer.customerId,
            userType = UserType.CUSTOMER,
        )
        customerToken = generateToken(customerAuthReq)

        // boss
        val boss = TestData.saveBoss(bossService)

        // market
        val market = TestData.saveMarket(marketService, boss)

        // quest
        testQuest = TestData.saveQuest(questService, market)

        // challenge
        testChallenge = TestData.saveChallenge(challengeService, testQuest, testCustomer)
    }

    @Test
    @DisplayName("챌린지를 등록하면 경험치가 누적된다.")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun test2(){
        saveTargetChallenge()
        val userAction = UserAction.CHALLENGE_REGISTER

        mockMvc.perform(
            post("/api/customer/challenge")
                .header(AUTHORIZATION, customerToken)
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "questId": "${testQuest.questId}",
                      "receiptImageId": "IM10000001"
                    }
                """)
        )
            .andDo {
                println(it.response)
                val i = it.response.contentAsString
                println(i)
            }
            .andExpect(status().isOk)
            .andExpect { it.response.toString().isEmpty() }
            .andExpect { it.response.toString().isNotEmpty() }

            /**
             * 경험치 이력이 저장된다.
             */
            .andDo {
                val list = xpHisService.findAll(
                    searchDto = XpSearchDto(userId = customerAuthReq.userId),
                    Pageable.ofSize(10)
                ).content

                println(list)
                Assertions.assertEquals(1, list.size)
                list.first().let {
                    Assertions.assertEquals(userAction.title, it.title)
                    Assertions.assertEquals(userAction.xpPoint, it.xpPoint)
                }
            }

            /**
             * 사용자의 누적 경험치가 갱신된다.
             */
            .andDo {
                val customer = customerService.findByAuthReq(customerAuthReq)
                val xpPoint = customer.xpPoint-(testCustomer.xpPoint ?: 0)
                Assertions.assertEquals(userAction.xpPoint, xpPoint)
            }
    }

    @Test
    @DisplayName("좋아요를 누르면 경험치가 누적된다.")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun test3(){
        saveTargetChallenge()
        val userAction = UserAction.LIKE

        mockMvc.perform(
            post("/api/customer/emoji")
                .header(AUTHORIZATION, customerToken)
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "targetId": "${testChallenge.challengeId}",
                      "targetType": "challenge",
                      "emojiType": "like"
                    }
                """)
        )
            .andDo { println(it.response.contentAsByteArray) }
            .andExpect(status().isOk)

            /**
             * 경험치 이력이 저장된다.
             */
            .andDo {
            val list = xpHisService.findAll(
                    searchDto = XpSearchDto(userId = customerAuthReq.userId),
                    Pageable.ofSize(10)
                ).content

                println(list)
                Assertions.assertEquals(1, list.size)
                list.first().let {
                    Assertions.assertEquals(userAction.title, it.title)
                    Assertions.assertEquals(userAction.xpPoint, it.xpPoint)
                }
            }

            /**
             * 사용자의 누적 경험치가 갱신된다.
             */
            .andDo {
                val customer = customerService.findByAuthReq(customerAuthReq)
                val xpPoint = customer.xpPoint-(testCustomer.xpPoint ?: 0)
                Assertions.assertEquals(userAction.xpPoint, xpPoint)
            }
    }
}