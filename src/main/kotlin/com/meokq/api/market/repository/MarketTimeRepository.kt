package com.meokq.api.market.repository

import com.meokq.api.market.model.MarketTime
import com.meokq.api.market.model.identifier.MarketTimeId
import org.springframework.data.jpa.repository.JpaRepository

interface MarketTimeRepository : JpaRepository<MarketTime, MarketTimeId> {
    fun findAllByMarketId(marketId : String) : List<MarketTime>
}