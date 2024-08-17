package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.TestData.missionReqForSave1
import com.meokq.api.TestData.missionReqForSave2
import com.meokq.api.TestData.rewardReqForSave1
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.market.model.Market
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.request.QuestCreateReq
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
    fun findAll() {
        // given
        val saveReq = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(missionReqForSave1, missionReqForSave2),
            rewards = listOf(rewardReqForSave1),
            )
        val searchDto = QuestSearchDto(
            status = QuestStatus.UNDER_REVIEW
        )
        val pageable = PageRequest.of(0, 10)

        // when
        service.save(saveReq)
        val result = service.findAll(searchDto, pageable)

        // then
        Assertions.assertTrue(!result.isEmpty)
    }

    @Test
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
        // when
        val questResp2 = service.adminSave(
            TestData.questCreateReqForAdmin)
        val findQuest2 = service.findById(questResp2.questId!!)

        Assertions.assertTrue{findQuest2.missionTitles?.isNotEmpty() == true}
        Assertions.assertTrue{findQuest2.rewardTitles?.isNotEmpty() == true}
    }


    @Test
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

    @Test
    @DisplayName("유저가 완료한 퀘스트만 조회 되어야 합니다.")
    fun completedQuests(){
        val authReqCS10000001 = AuthReq(
            userId = "110804aa-a3f9-4894-93d9-9b446e583b27",
            userType = UserType.CUSTOMER,
        )
        val pageable = PageRequest.of(0, 10)
        val expectList = listOf("8d21793d-261f-4c78-b140-0296e169e6a0","a1f1ac10-9dcd-4a62-bbef-3e0ab69b7bfe")


        val result = service.getCompletedQuests(pageable, authReqCS10000001)
        Assertions.assertIterableEquals(expectList, result.content.map { it.questId })
    }

    @Test
    @DisplayName("유저가 완료하지 않은 퀘스트만 조회 되어야 합니다.")
    fun uncompletedQuests(){
        val authReqCS10000001 = AuthReq(
            userId = "110804aa-a3f9-4894-93d9-9b446e583b27",
            userType = UserType.CUSTOMER,
        )
        val pageable = PageRequest.of(0, 10)
        val expectList = listOf(
            "832a1c95-c300-471a-919e-0e767978e1e2",
            "a2b01530-7d17-4178-857b-35a5d4d7e2d6",
            "58cc11d5-b4c7-4762-b7a0-67b001e40272",
            "efc2b619-8754-4f79-88c3-0136cbf57d58",
            "bdf20dca-8d59-4c84-bfb9-5465cabd4eef",
            "a30b85b1-1f4c-44b4-9462-0b6040845e52",
            "c801b910-0eba-48a8-8293-2ce89473d5a4",
            "fb0e5aae-edc6-4a9e-9d1e-3fbfd14f134c",
            "2e1702a3-5e40-44bd-a557-8bad3330d5ec",
            "63312ed3-9cb6-493e-8f2a-3262ec5d961a")

        val result = service.getUncompletedQuests(pageable, authReqCS10000001)
        Assertions.assertIterableEquals(expectList, result.content.map { it.questId })
    }

}