package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.TestData.missionReqForSave1
import com.meokq.api.TestData.missionReqForSave2
import com.meokq.api.TestData.rewardReqForSave1
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.request.ChallengeSaveReq
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
internal class QuestServiceTest: QuestBaseTest() {
    @Autowired private lateinit var service: QuestService


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
            userId = "CS10000001",
            userType = UserType.CUSTOMER,
        )
        val pageable = PageRequest.of(0, 10)

        val completedQuests = service.getCompletedQuests(pageable, authReqCS10000001)
        val completedQuestIds = completedQuests.content.map { it.questId }.toSet()

        completedQuests.content.forEach { quest ->
            Assertions.assertTrue(
                completedQuestIds.contains(quest.questId)
            )
        }
    }

    @Test
    @DisplayName("유저가 완료하지 않은 퀘스트만 조회 되어야 합니다.")
    fun uncompletedQuests(){
        val authReqCS10000001 = AuthReq(
            userId = "CS10000001",
            userType = UserType.CUSTOMER,
        )
        val pageable = PageRequest.of(0, 10)


        val result = service.getUncompletedQuests(pageable, authReqCS10000001)
        val completedQuests = service.getCompletedQuests(pageable, authReqCS10000001)
        val completedQuestIds = completedQuests.content.map { it.questId }.toSet()

        result.content.forEach { quest ->
            Assertions.assertFalse(
                completedQuestIds.contains(quest.questId)
            )
        }
    }

    @Test
    @DisplayName("퀘스트가 soft 삭제되면 퀘스트는 조회되지 않아야 한다")
    fun deleteTest(){
        val questId = "832a1c95-c300-471a-919e-0e767978e1e2"
        service.softDelete(questId)
        val searchDto = QuestSearchDto(
            status = QuestStatus.PUBLISHED
        )
        val pageable = PageRequest.of(0, 10)
        val result = service.findAll(searchDto, pageable)

        // when
        val questExists = result.content.any { it.questId == questId }
        Assertions.assertFalse(questExists, "삭제된 퀘스트가 조회되지 않아야 합니다.")
    }

    @Test
    @DisplayName("퀘스트가 삭제된 퀘스트도 조회가 되어야 한다.")
    fun deleteTest1(){
        val resp = service.adminSave(TestData.questCreateReqForAdmin)

        service.softDelete(resp.questId!!)
        val searchDto = QuestSearchDto(
            status = QuestStatus.DELETED
        )
        val pageable = PageRequest.of(0, 10)
        val result = service.findAll(searchDto, pageable)
        val questExists = result.content.any { it.questId == resp.questId }
        Assertions.assertTrue(questExists)
    }

    @Test
    @DisplayName("퀘스트가 hard 삭제되면 퀘스트는 조회되지 않아야 한다")
    fun deleteTest2(){
        val resp = service.adminSave(TestData.questCreateReqForAdmin)
        service.hardDelete(resp.questId!!, TestData.authReqAdmin)
        val searchDto = QuestSearchDto(
            questId = resp.questId
        )
        val pageable = PageRequest.of(0, 10)
        val result = service.findAll(searchDto, pageable)

        Assertions.assertTrue(result.content.isEmpty())
    }

    @Test
    @DisplayName("퀘스트가 hard 삭제되면 xp가 회수되어야 한다.")
    fun deleteTest3(){
        val resp = questService.adminSave(TestData.questCreateReqForAdmin)
        challengeService.save(
            ChallengeSaveReq(
                resp.questId!!,"IM10000001"),TestData.authReqCS10000001)
        service.hardDelete(resp.questId!!, TestData.authReqAdmin)

        val mockCustomer = customerService.findModelById(TestData.customerCS10000001.customerId!!)

        Assertions.assertEquals( 0,mockCustomer.xpPoint)
    }

}