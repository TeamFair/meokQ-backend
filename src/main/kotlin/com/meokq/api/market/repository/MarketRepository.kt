package com.meokq.api.market.repository

import com.meokq.api.market.model.Market
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface MarketRepository : JpaRepository<Market, String>{
    fun findAllByPresidentId(bossId : String) : List<Market>

    fun findAll(spec: Specification<Market>, pageable: Pageable?) : Page<Market>
}