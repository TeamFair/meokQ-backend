package com.meokq.api.quest.service

import com.meokq.api.TestData.missionReqForSave1
import com.meokq.api.TestData.missionReqForSave2
import com.meokq.api.TestData.rewardReqForSave1
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.market.model.Market
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.user.service.BossService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class QuestServiceTest {
    @Autowired private lateinit var service: QuestService
    @Autowired private lateinit var bossService: BossService
    @Autowired private lateinit var marketService: MarketService

    @Test
    //@Transactional
    fun findAll() {
        // given
        val searchDto = QuestSearchDto(
            marketId = "MK00000001",
            //questId = "QS00000001",
            //status = QuestStatus.UNDER_REVIEW
        )

        val saveReq = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1)
        )

        val pageable = PageRequest.of(0, 10)

        // when
        service.save(saveReq)
        val result = service.findAll(searchDto, pageable)

        // then
        Assertions.assertTrue(!result.isEmpty)
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
    @DisplayName("경험치를 제공하는 퀘스트를 등록합니다.")
    fun registerExperienceTypeQuest(){
        // given
        val market = marketService.saveModel(Market())
        val xpReward = RewardReq(
            type = RewardType.XP,
            target = null,
            quantity = 30,
            content = null,
            discountRate = null,
        )
        val questReq = QuestCreateReq(
            marketId = market.marketId!!,
            missions = listOf(missionReqForSave1),
            rewards = listOf(xpReward),
        )

        // when
        val questResp1 = service.save(questReq)
        val findQuest1 = service.findById(questResp1.questId!!)

        Assertions.assertEquals(questReq.marketId, findQuest1.marketId)
        Assertions.assertTrue{findQuest1.missionTitles?.isNotEmpty() == true}
        Assertions.assertTrue{findQuest1.rewardTitles?.isNotEmpty() == true}
    }

    @Test
    @DisplayName("경험치를 제공하는 퀘스트를 등록합니다.(관리자 등록시)")
    fun registerExperienceTypeQuestAdmin(){
        // given
        val xpReward = RewardReq(
            type = RewardType.XP,
            target = null,
            quantity = 30,
            content = null,
            discountRate = null,
        )
        val adminReq = QuestCreateReqForAdmin(
            missions = listOf(missionReqForSave1),
            rewards = listOf(xpReward),
            expireDate = "9999-12-31"
        )
        val adminAuthReq = AuthReq(
            userType = UserType.ADMIN
        )

        // when
        val questResp2 = service.adminSave(adminReq, adminAuthReq)
        val findQuest2 = service.findById(questResp2.questId!!)

        Assertions.assertTrue{findQuest2.missionTitles?.isNotEmpty() == true}
        Assertions.assertTrue{findQuest2.rewardTitles?.isNotEmpty() == true}
    }


    @Test
    //@Transactional
    fun findById() {
        // given
        val marketId = "MK00000001"
        val saveReq = QuestCreateReq(
            marketId = marketId,
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1)
        )

        // when
        val saveResp = service.save(saveReq)
        Assertions.assertNotNull(saveResp.questId)

        val resp = service.findById(saveResp.questId!!)

        // then
        Assertions.assertEquals(saveResp.questId, resp.questId)
        Assertions.assertEquals(marketId, resp.marketId)
        Assertions.assertTrue(resp.missionTitles!!.isNotEmpty())
        Assertions.assertTrue(resp.rewardTitles!!.isNotEmpty())
    }
}