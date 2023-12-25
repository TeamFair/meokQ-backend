package com.meokq.api.market.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.market.model.MarketAuth

interface MarketAuthRepository : BaseRepository<MarketAuth, String>{
    //fun findAll(spec: Specification<MarketAuth>, pageable: Pageable): Page<MarketAuth>
}