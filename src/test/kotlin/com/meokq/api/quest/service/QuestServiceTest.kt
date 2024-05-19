package com.meokq.api.quest.service

import com.meokq.api.TestData.missionReqForSave1
import com.meokq.api.TestData.missionReqForSave2
import com.meokq.api.TestData.rewardReqForSave1
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestSearchDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class QuestServiceTest {
    @Autowired
    private lateinit var service: QuestService

    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Test
    @Transactional
    fun findAll() {
        // given
        val req = QuestSearchDto(
            marketId = "MK00000001",
            questId = "QS00000001",
            status = QuestStatus.UNDER_REVIEW
        )

        val pageable = PageRequest.of(0, 10)

        // when
        val result = service.findAll(req, pageable)

        // then
        Assertions.assertTrue(!result.isEmpty)
        Assertions.assertEquals(req.questId, result.first().questId)
        Assertions.assertEquals(req.marketId, result.first().marketId)
        Assertions.assertEquals(req.status, result.first().status)
    }

    @Test
    //@Transactional
    @Transactional(rollbackFor = [Exception::class])
    fun save() {
        // given
        val req = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1)
        )

        // when
        val result = service.save(req)
        val searchData = service.findById(result.questId!!)

        // then
        Assertions.assertNotNull(result.questId)

        val missionTitles = req.missions.map { MissionType.getTitle(Mission(it)) }
        Assertions.assertIterableEquals(missionTitles, searchData.missionTitles)

        val rewardTitles = req.rewards.map { RewardType.getTitle(Reward(it)) }
        Assertions.assertIterableEquals(rewardTitles, searchData.rewardTitles)
    }

    @Test
    @Transactional(rollbackFor = [Exception::class])
    fun adminSave() {
        // given
        val req = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1),
            expireDate = "2024-12-31"
        )
        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)

        // when
        val result = service.adminSave(req, authReq)
        val searchData = service.findById(result.questId!!)
        val parseDate = LocalDate.parse(req.expireDate).atTime(0, 0, 0)

        // then
        Assertions.assertNotNull(result.questId)

        val missionTitles = req.missions.map { MissionType.getTitle(Mission(it)) }
        Assertions.assertIterableEquals(missionTitles, searchData.missionTitles)

        val rewardTitles = req.rewards.map { RewardType.getTitle(Reward(it)) }
        Assertions.assertIterableEquals(rewardTitles, searchData.rewardTitles)

        Assertions.assertEquals(QuestStatus.PUBLISHED, searchData.status)
        Assertions.assertEquals(parseDate, searchData.expiredData)
    }

    @Test
    @Transactional(rollbackFor = [Exception::class])
    @DisplayName("어드민 사용자가 등록한 퀘스트는 등록자가 저장된다.") // TODO : 추후에는 모든 테이블에 createdBy, upatedBy 컬럼을 사용하도록 수정해야 함.
    fun adminSave2() {
        // given
        val req = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1),
            expireDate = "2024-12-31"
        )

        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)

        // when
        val result = service.adminSave(req, authReq)
        val searchData2 = service.findModelById(result.questId!!)

        // then
        Assertions.assertNotNull(result.questId)
        Assertions.assertEquals(authReq.userId, searchData2.createdBy)
    }

    @Test
    @Transactional
    fun findById() {
        // given
        val questId = "QS00000001"
        val marketId = "MK00000001"

        // when
        val resp = service.findById(questId)

        // then
        Assertions.assertEquals(questId, resp.questId)
        Assertions.assertEquals(marketId, resp.marketId)
        Assertions.assertTrue(resp.missionTitles!!.isNotEmpty())
        Assertions.assertTrue(resp.rewardTitles!!.isNotEmpty())
    }
}