package com.meokq.api.market.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.market.model.Market

interface MarketRepository : BaseRepository<Market, String>{
    fun findAllByPresidentId(bossId : String) : List<Market>

    //fun findAll(spec: Specification<Market>, pageable: Pageable?) : Page<Market>
}