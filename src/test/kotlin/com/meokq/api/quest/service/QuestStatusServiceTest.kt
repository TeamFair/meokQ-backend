package com.meokq.api.quest.service

import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.QuestDeleteReq
import com.meokq.api.quest.request.QuestPublishReq
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("local")
internal class QuestStatusServiceTest: QuestBaseTest(){
    @Autowired
    private lateinit var service: QuestStatusService

    @Test
    @DisplayName("퀘스트 상태가 '게시됨'으로 바뀌고, 게시만료날짜가 정해진다.")
    @Transactional
    fun publishQuest() {
        // given
        val req = QuestPublishReq(
            questId = testQuest01.questId!!,
            marketId = testMarket.marketId!!,
            ticketCount = 2,
        )
        val expireDate = LocalDate.now()
            .plusDays(30 * req.ticketCount)

        // when
        val quest = service.publishQuest(req)

        // then
        Assertions.assertEquals(QuestStatus.PUBLISHED, quest.status)
        Assertions.assertEquals(expireDate, quest.expireDate?.toLocalDate())
    }

    @Test
    @DisplayName("퀘스트 상태가 '삭제됨'으로 바뀐다.")
    @Transactional
    fun deleteQuest() {
        // given
        val req = QuestDeleteReq(
            questId = testQuest01.questId!!,
            marketId = testMarket.marketId!!,
        )

        // when
        val quest = service.deleteQuest(req)

        // then
        Assertions.assertEquals(QuestStatus.DELETED, quest.status)
    }
}