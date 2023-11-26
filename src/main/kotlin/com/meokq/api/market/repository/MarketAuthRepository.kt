package com.meokq.api.market.repository

import com.meokq.api.market.model.MarketAuth
import org.springframework.data.jpa.repository.JpaRepository

interface MarketAuthRepository : JpaRepository<MarketAuth, String>{
}