package com.meokq.api.application.repository

import com.meokq.api.application.model.Market
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MarketRepository : JpaRepository<Market, String> {

    fun findByDistrict(district: String?, pageable: Pageable?): Page<Market>
}