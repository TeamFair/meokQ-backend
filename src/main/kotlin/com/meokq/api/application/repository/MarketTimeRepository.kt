package com.meokq.api.application.repository

import com.meokq.api.application.model.MarketTime
import com.meokq.api.application.model.MarketTimeId
import org.springframework.data.jpa.repository.JpaRepository

interface MarketTimeRepository : JpaRepository<MarketTime, MarketTimeId> {
    fun findAllByMarketId(marketId : String) : List<MarketTime>
}