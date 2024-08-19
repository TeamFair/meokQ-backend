package com.meokq.api.challenge.service

import com.meokq.api.TestData
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.emoji.repository.EmojiRepository
import com.meokq.api.file.service.ImageService
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.rank.ChallengeEmojiRankService
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.service.AdminService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Transactional
@SpringBootTest
@ActiveProfiles("local")
@Rollback
internal class ChallengeServiceTest : ChallengeBaseTest(){
    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Autowired
    private lateinit var service: ChallengeService

    @Autowired
    private lateinit var questRepository: QuestRepository

    @Autowired
    private lateinit var emojiRepository: EmojiRepository

    @Autowired
    private lateinit var adminService: AdminService

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var challengeRepository: ChallengeRepository

    @Autowired
    private lateinit var challengeEmojiRankService: ChallengeEmojiRankService

    @Autowired
    private lateinit var imageService: ImageService

    @Test
    @DisplayName("챌린지를 정상적으로 등록합니다.")
    fun save() {
        // given
        val request = ChallengeSaveReq(
            questId = testQuest01.questId!!,
            receiptImageId = "IM10000001",
        )

        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = testCustomer02.customerId,
        )

        // when
        val resp = challengeService.save(request, authReq)

        // then
        assertEquals(authReq.userId, resp.customerId)
        assertEquals(request.questId, resp.questId)
        assertEquals(request.receiptImageId, resp.receiptImageId)
        assertEquals(ChallengeStatus.UNDER_REVIEW, resp.status)
    }

    @Test
    @Transactional
    @DisplayName("관리자가 등록한 퀘스트라면, 챌린지는 등록시 자동 승인된다.")
    fun save2(){
        // given
        val customerAuthReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = testCustomer01.customerId,
        )

        val adminAuthReq = AuthReq(
            userType = UserType.ADMIN,
            userId = adminId
        )

        val questReq = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            writer = "일상 테스트 작성자",
            imageId = "IM10000001",
            expireDate = "2024-12-31"
        )

        // when
        setSecurityContext(adminAuthReq)
        val questResp = questService.adminSave(
            request = questReq)
        Assertions.assertNotNull(questResp.questId)

        setSecurityContext(customerAuthReq)
        val challengeResp = challengeService.save(
            ChallengeSaveReq(
                questId = questResp.questId!!,
                receiptImageId = "IM10000001",
            ),
            customerAuthReq
        )

        // then
        Assertions.assertNotNull(challengeResp.challengeId)
        val challengeSearchResp = challengeService.findModelById(challengeResp.challengeId!!)
        assertEquals(ChallengeStatus.APPROVED, challengeSearchResp.status)
    }

    @Test
    @Transactional
    @DisplayName("관리자가 등록한 퀘스트가 아니라면, 챌린지는 UNDER_REVIEW 상태이다.")
    fun save3(){
        // given
        val customerAuthReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "${UUID.randomUUID()}",
        )

        val questReq = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1)
        )

        // when
        val questResp = questService.save(questReq)
        Assertions.assertNotNull(questResp)

        val challengeResp = challengeService.save(
            ChallengeSaveReq(
                questId = questResp.questId!!,
                receiptImageId = "IM10000001",
            ),
            customerAuthReq
        )

        // then
        Assertions.assertNotNull(challengeResp.challengeId)
        val challengeSearchResp = challengeService.findModelById(challengeResp.challengeId!!)
        assertEquals(ChallengeStatus.UNDER_REVIEW, challengeSearchResp.status)
    }

    @Test
    fun findById() {
        // given
        val challengeId = testChallenge01.challengeId!!
        val questId = testQuest01.questId

        // when
        val resp = challengeService.findById(challengeId)

        // then
        assertEquals(challengeId, resp.challengeId)
        assertEquals(questId, resp.quest?.questId)
        Assertions.assertTrue(resp.quest?.rewards?.isNotEmpty()!!)
        Assertions.assertTrue(resp.quest?.missions?.isNotEmpty()!!)
    }


    @Test
    fun deleteById() {
        // given
        val customer = TestData.saveCustomer(customerService)
        val challenge = TestData.saveChallenge(challengeService, testQuest01, customer)
        val challengeId = challenge.challengeId!!
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = customer.customerId
        )

        // when
        Assertions.assertDoesNotThrow { challengeService.delete(challengeId, authReq) }
    }

    @Test
    @DisplayName("검토중인 도전내역만 삭제할수 있습니다.")
    fun deleteById2() {
        // given
        val customer = TestData.saveCustomer(customerService)
        val challenge = TestData.saveChallenge(challengeService, testQuest01, customer).copy()
        challenge.status = ChallengeStatus.APPROVED
        val challengeResp = challengeService.saveModel(challenge)

        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = customer.customerId
        )
        setSecurityContext(authReq)
        val challengeId = challengeResp.challengeId

        // when
        Assertions.assertThrows(InvalidRequestException::class.java) {
            challengeService.delete(challengeId!!, authReq)
        }
    }

    @Test
    @DisplayName("도전 내역을 등록한 계정으로만 도전내역을 삭제할 수 있습니다.")
    fun deleteById3() {
        // given
        val newCustomer = TestData.saveCustomer(customerService)
        val challengeId = testAdminChallenge.challengeId!!
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = newCustomer.customerId
        )

        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            challengeService.delete(challengeId, authReq)
        }
    }

}
