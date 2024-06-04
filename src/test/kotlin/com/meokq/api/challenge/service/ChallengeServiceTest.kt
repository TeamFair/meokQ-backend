package com.meokq.api.challenge.service

import com.meokq.api.TestData
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.service.QuestService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class ChallengeServiceTest {

    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Autowired
    private lateinit var service: ChallengeService

    @Autowired
    private lateinit var questService: QuestService

    @Test
    fun save() {
        // given
        val request = ChallengeSaveReq(
            questId = "QS10000001",
            receiptImageId = "IM10000001",
        )

        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS10000001",
        )

        // when
        val resp = service.save(request, authReq)

        // then
        Assertions.assertEquals(authReq.userId, resp.customerId)
        Assertions.assertEquals(request.questId, resp.questId)
        Assertions.assertEquals(request.receiptImageId, resp.receiptImageId)
        Assertions.assertEquals(ChallengeStatus.UNDER_REVIEW, resp.status)
    }

    @Test
    @Transactional
    @DisplayName("관리자가 등록한 퀘스트라면, 챌린지는 등록시 자동 승인된다.")
    fun save2(){
        // given
        val customerAuthReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS10000001",
        )

        val adminAuthReq = AuthReq(
            userType = UserType.ADMIN,
            userId = adminId
        )

        val questReq = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            expireDate = "2024-12-31"
        )

        // when
        val questResp = questService.adminSave(questReq, adminAuthReq)
        Assertions.assertNotNull(questResp.questId)

        val challengeResp = service.save(
            ChallengeSaveReq(
                questId = questResp.questId!!,
                receiptImageId = "IM10000001",
            ),
            customerAuthReq
        )

        // then
        Assertions.assertNotNull(challengeResp.challengeId)
        val challengeSearchResp = service.findModelById(challengeResp.challengeId!!)
        Assertions.assertEquals(ChallengeStatus.APPROVED, challengeSearchResp.status)
    }

    @Test
    @Transactional
    @DisplayName("관리자가 등록한 퀘스트가 아니라면, 챌린지는 UNDER_REVIEW 상태이다.")
    fun save3(){
        // given
        val customerAuthReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS10000001",
        )

        val questReq = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1)
        )

        // when
        val questResp = questService.save(questReq)
        Assertions.assertNotNull(questResp.questId)

        val challengeResp = service.save(
            ChallengeSaveReq(
                questId = questResp.questId!!,
                receiptImageId = "IM10000001",
            ),
            customerAuthReq
        )

        // then
        Assertions.assertNotNull(challengeResp.challengeId)
        val challengeSearchResp = service.findModelById(challengeResp.challengeId!!)
        Assertions.assertEquals(ChallengeStatus.UNDER_REVIEW, challengeSearchResp.status)
    }

    @Test
    fun findById() {
        // given
        val challengeId = "CH10000001"
        val questId = "QS10000001"

        // when
        val resp = service.findById(challengeId)

        // then
        Assertions.assertEquals(challengeId, resp.challengeId)
        Assertions.assertEquals(questId, resp.quest?.questId)
        Assertions.assertTrue(resp.quest?.rewards?.isNotEmpty()!!)
        Assertions.assertTrue(resp.quest?.missions?.isNotEmpty()!!)
    }

    @Test
    fun deleteById() {
        // given
        val challengeId = "CH10000001"
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS10000001"
        )

        // when
        Assertions.assertDoesNotThrow { service.delete(challengeId, authReq) }
    }

    @Test
    @DisplayName("검토중인 도전내역만 삭제할수 있습니다.")
    fun deleteById2() {
        // given
        val challengeId = "CH10000003"
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS10000001"
        )

        // when
        Assertions.assertThrows(InvalidRequestException::class.java) {
            service.delete(challengeId, authReq)
        }
    }

    @Test
    @DisplayName("도전 내역을 등록한 계정으로만 도전내역을 삭제할 수 있습니다.")
    fun deleteById3() {
        // given
        val challengeId = "CH10000003"
        val authReq = AuthReq(
            userType = UserType.CUSTOMER,
            userId = "CS90000001"
        )

        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            service.delete(challengeId, authReq)
        }
    }

    @Test
    @DisplayName("LIKE 이모지가 10개 이상인 challenge와 10개 이하의 challenge가 번갈아가며 조회되어야 한다.")
    fun randomSelect() {
     /*   val emoji1 = Emoji(id = "emoji1Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "1a1435c3-8695-45e0-aba2-05365eade0d3")
        val emoji2 = Emoji(id = "emoji2Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "1a1435c3-8695-45e0-aba2-05365eade0d3")
        val emoji3 = Emoji(id = "emoji3Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "1a1435c3-8695-45e0-aba2-05365eade0d3")
        val emoji4 = Emoji(id = "emoji4Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "1a1435c3-8695-45e0-aba2-05365eade0d3")
        val emoji5 = Emoji(id = "emoji5Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "1a1435c3-8695-45e0-aba2-05365eade0d3")
        val emoji6 = Emoji(id = "emoji6Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "b391d3e2-f9fa-4c54-94df-5aebce941d41")
        val emoji7 = Emoji(id = "emoji7Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "2c54d6b4-4940-410a-95af-1a396b304ea5")
        val emoji8 = Emoji(id = "emoji8Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "2c54d6b4-4940-410a-95af-1a396b304ea5")
        val emoji9 = Emoji(id = "emoji9Id", status = EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "2c54d6b4-4940-410a-95af-1a396b304ea5")
        val emoji10 = Emoji(id = "emoji10Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji11 = Emoji(id = "emoji11Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji12 = Emoji(id = "emoji12Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji13 = Emoji(id = "emoji13Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji14 = Emoji(id = "emoji14Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji15 = Emoji(id = "emoji15Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
        val emoji16 = Emoji(id = "emoji16Id", status =EmojiStatus.LIKE, targetType = TargetType.CHALLENGE, targetId = "5660fea4-6596-407c-946d-dbc3c926eb56")
*/

    }
}