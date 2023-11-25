package com.meokq.api.application.repository

import com.meokq.api.application.model.MarketAuth
import org.springframework.data.jpa.repository.JpaRepository

interface MarketAuthRepository : JpaRepository<MarketAuth, String>{
}