package com.meokq.api.market.repository

import com.meokq.api.market.model.MarketAuth
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface MarketAuthRepository : JpaRepository<MarketAuth, String>{
    fun findAll(spec: Specification<MarketAuth>, pageable: Pageable): Page<MarketAuth>
}