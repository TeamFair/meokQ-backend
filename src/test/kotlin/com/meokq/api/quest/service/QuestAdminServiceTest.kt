package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.filters.JwtFilter
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.QuestCreateReqForAdmin
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
class QuestAdminServiceTest {
    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Autowired
    private lateinit var jwtFilter: JwtFilter

    @Autowired
    private lateinit var service: QuestService

    @Test
    @Transactional(rollbackFor = [Exception::class])
    @DisplayName("관리자는 퀘스트를 등록할 수 있다.")
    fun adminSave() {
        // given
        val req = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            expireDate = "2024-12-31"
        )
        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)

        // when
        val result = service.adminSave(req)
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
    @DisplayName("어드민 사용자가 등록한 퀘스트는 등록자가 저장된다.")
    fun adminSave2() {
        // given
        val req = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            expireDate = "2024-12-31"
        )

        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)
        jwtFilter.setSecurityContext(authReq)

        // when
        val result = service.adminSave(req)
        val searchData2 = service.findModelById(result.questId!!)

        // then
        Assertions.assertNotNull(result.questId)
        Assertions.assertEquals(authReq.userId, searchData2.createdBy)
    }

    @Test
    @Transactional(rollbackFor = [Exception::class])
    @DisplayName("마켓 아이디가 null인 항목이 조회된다.")
    fun findAll() {
        // given
        val searchDto = QuestSearchDto()
        val saveReq = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            expireDate = "2024-12-31"
        )

        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)
        jwtFilter.setSecurityContext(authReq)

        val pageable = PageRequest.of(0, 10)

        // when
        val saveResp = service.adminSave(saveReq)
        val result = service.findAll(searchDto, pageable)

        // then
        Assertions.assertTrue(!result.isEmpty)
        Assertions.assertTrue(result.content.any { it.questId == saveResp.questId })

        val searchData = result.content.filter { it.questId == saveResp.questId }.first()
        Assertions.assertNotNull(searchData.missionTitle)
        Assertions.assertNotNull(searchData.rewardTitle)
        Assertions.assertNull(searchData.marketId)
    }

    @Test
    @Transactional(rollbackFor = [Exception::class])
    @DisplayName("관리자가 등록한 퀘스트만 조회 되어야 한다.")
    fun findAll2() {
        // given
        val searchDto = QuestSearchDto(
            status = QuestStatus.PUBLISHED,
            creatorRole = UserType.ADMIN
        )

        val saveReq = QuestCreateReqForAdmin(
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1),
            expireDate = "2024-12-31",
        )

        val authReq = AuthReq(userId = adminId, userType = UserType.ADMIN)
        jwtFilter.setSecurityContext(authReq)

        val pageable = PageRequest.of(0, 10)

        // when
        val saveResp = service.adminSave(saveReq)
        val result = service.findAll(searchDto, pageable)

        // then
        Assertions.assertTrue(!result.isEmpty)
        Assertions.assertTrue(result.content.any { it.questId == saveResp.questId })

        val searchData = result.content.filter { it.questId == saveResp.questId }.first()

        Assertions.assertEquals("ADMIN", searchData.creatorRole)
    }


}