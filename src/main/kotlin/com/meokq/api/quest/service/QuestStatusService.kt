package com.meokq.api.quest.service

import com.meokq.api.core.JpaService
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestDeleteReq
import com.meokq.api.quest.request.QuestPublishReq
import com.meokq.api.ticket.TicketService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QuestStatusService(
    private val repository : QuestRepository,
    private val ticketService: TicketService,
): JpaService<Quest, String> {
    override var jpaRepository: JpaRepository<Quest, String> = repository

    fun publishQuest(req : QuestPublishReq): Quest {
        val model = findModelById(req.questId)
        ticketService.useTicket(req.marketId, req.ticketCount, model)
        model.status = model.status.publishAction()
        model.expireDate = calculateExpireDate(req.ticketCount)
        return saveModel(model)
    }

    // TODO : 추후 종료상태 변경은 배치로 처리해야 함.

    fun deleteQuest(req : QuestDeleteReq): Quest {
        //todo : 해당마켓에서 등록한 퀘스트가 맞는지, 요청자가 해당 마켓의 관리자인지 확인해야 함.
        val model = findModelById(req.questId)
        model.status = model.status.deleteAction()
        return saveModel(model)
    }

    // 2023-12-25 이용권 1개당 30일씩 추가됨
    private fun calculateExpireDate(ticketCount: Long): LocalDateTime {
        return LocalDateTime.now().plusDays(30 * ticketCount)
    }
}