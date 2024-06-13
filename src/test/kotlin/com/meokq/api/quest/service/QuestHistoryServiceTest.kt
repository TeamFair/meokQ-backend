package com.meokq.api.quest.service

import com.meokq.api.quest.repository.QuestHistoryRepository
import com.meokq.api.quest.repository.QuestRepository
import org.junit.jupiter.api.Assertions.*
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
class QuestHistoryServiceTest {
    @Autowired
    private lateinit var service: QuestHistoryService
    @Autowired
    private lateinit var repository: QuestHistoryRepository



    @Test
    @DisplayName("퀘스트아이디와 유저아이디를 받아서 객체를 저장 한다.")
    fun save() {
        val questId = "QS00000001"
        val customerId = "US00000001"

        val result = service.save(questId, customerId)

        assertEquals(questId, result.questId)
    }

    @Test
    @DisplayName("유저가 완료한 퀘스트가 나와야 한다.")
    fun findByCustomerId() {
        val customerId = "110804aa-a3f9-4894-93d9-9b446e583b27"
        val pageable = PageRequest.of(0,10)
        val expectList = listOf("8d21793d-261f-4c78-b140-0296e169e6a0","a1f1ac10-9dcd-4a62-bbef-3e0ab69b7bfe")

        val content = service.findByCustomerId(customerId, pageable)
        val ids = content.map { it.questId }
        assertIterableEquals(expectList,ids)
    }

    @Test
    @DisplayName("유저가 미완료한 퀘스트가 나오면 안된다.")
    fun assertFalseUnCompletedQuests() {
        val customerId = "110804aa-a3f9-4894-93d9-9b446e583b27"
        val pageable = PageRequest.of(0,10)
        val expectList = listOf("6f5a6bb3-4b6b-4286-bb77-d03bf17e7a2f","cc80c8f1-2775-4ec4-8f50-7d8c23f2eac8","a8e65460-58f5-494a-9a5e-1d81c6d372f2")

        val content = service.findByCustomerId(customerId, pageable)
        val ids = content.map { it.questId }

        ids.forEach {
            assertFalse(expectList.contains(it))
        }
    }

}