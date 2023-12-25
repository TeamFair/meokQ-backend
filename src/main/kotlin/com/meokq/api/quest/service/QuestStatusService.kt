package com.meokq.api.quest.service

import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestDeleteReq
import com.meokq.api.quest.request.QuestPublishReq
import com.meokq.api.ticket.TicketService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QuestStatusService(
    private val ticketService: TicketService,
    private val questService: QuestService,
) {

    fun publishQuest(req : QuestPublishReq): Quest {
        val model = questService.findModelById(req.questId)
        model.status = model.status.publishAction()
        // 2023-12-25 이용권 1개당 30일씩 추가됨
        ticketService.useTicket(req.marketId, req.ticketCount, model)
        model.expireDate = LocalDateTime.now().plusDays(30 * req.ticketCount)
        return questService.saveModel(model)
    }

    // TODO : 추후 종료상태 변경은 배치로 처리해야 함.

    fun deleteQuest(req : QuestDeleteReq) : Quest {
        //todo : 해당마켓에서 등록한 퀘스트가 맞는지, 요청자가 해당 마켓의 관리자인지 확인해야 함.
        val model = questService.findModelById(req.questId)
        model.status = model.status.deleteAction()
        return questService.saveModel(model)
    }
}