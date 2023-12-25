package com.meokq.api.ticket

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.model.Market
import com.meokq.api.market.service.MarketService
import com.meokq.api.quest.model.Quest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class TicketService(
    private val marketService: MarketService,
    private val repository: TicketRepository
) : BaseService<TicketHis, TicketHis, TicketHis, String> {
    override var _repository: JpaRepository<TicketHis, String> = repository
    override var _converter: BaseConverter<TicketHis, TicketHis, TicketHis> = TicketConverter

    fun buyTicket(request: TicketPmtReq) : TicketHis {
        return super.saveModel(TicketHis(request))
    }

    fun approvePmt(recordId : String): Market {
        val pmtHis = findModelById(recordId)
        val market = marketService.findModelById(pmtHis.marketId!!)
        market.ticketCount = market.ticketCount.plus(pmtHis.amount?:0)
        return marketService.saveModel(market)
    }

    fun findAll(){

    }

    fun useTicket(
        marketId : String,
        amount: Long,
        quest: Quest,
    ): Market {
        val market = marketService.findModelById(marketId)
        if (market.ticketCount <= 0) throw InvalidRequestException("쿠폰개수를 확인해주세요. (남은 쿠폰 개수 : ${market.ticketCount})")
        market.ticketCount-=amount

        // 쿠폰 사용 내역 저장
        saveModel(
            TicketHis(
                marketId = market.marketId,
                amount = amount,
                traceType = TraceType.USE,
                questId = quest.questId,
            )
        )

        return marketService.saveModel(market)
    }

    fun addTicket(pmtHis: TicketHis): Market {
        val market = marketService.findModelById(pmtHis.marketId!!)
        market.ticketCount += pmtHis.amount?:0
        return marketService.saveModel(market)
    }
}