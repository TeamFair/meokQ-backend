package com.meokq.api.market.repository

import com.meokq.api.market.model.MarketTime
import com.meokq.api.market.model.identifier.MarketTimeId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MarketTimeRepository : JpaRepository<MarketTime, MarketTimeId> {
    fun findAllByMarketId(marketId : String) : List<MarketTime>

    @Modifying
    @Query("delete from tb_market_time where marketId=:marketId")
    fun deleteAllByMarketId(marketId: String) : Int
}