package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.TestData.rewardReqForSave1
import com.meokq.api.TestData.rewardReqForSave2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class RewardServiceTest : QuestBaseTest() {
    @Autowired
    private lateinit var service: RewardService

    @Test
    @Transactional
    fun saveAll() {
        // given
        val questId = "sample-quest"
        val rewards = mutableListOf(rewardReqForSave1, rewardReqForSave2)

        // when
        val result = service.saveAll(questId, rewards)

        // then
        Assertions.assertEquals(rewards.size, result.size)
    }

    @Test
    @Transactional
    fun findModelsByQuestId() {
        // given
        val boss = TestData.saveBoss(bossService)
        val market = TestData.saveMarket(marketService, boss)
        val quest = TestData.saveQuest(questService, market)
        val questId = quest.questId!!

        // when
        val result = service.findModelsByQuestId(questId)

        // then
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    @Transactional
    fun deleteAllByQuestId() {
        // given
        val boss = TestData.saveBoss(bossService)
        val market = TestData.saveMarket(marketService, boss)
        val quest = TestData.saveQuest(questService, market)
        val questId = quest.questId!!

        // when
        service.deleteAllByQuestId(questId)

        // then
        val result = service.findModelsByQuestId(questId)
        Assertions.assertTrue(result.isEmpty())
    }
}