package com.meokq.api.application.repository

import com.meokq.api.application.model.Quest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<Quest, String> {
    fun findAllByMarketId(marketId : String, pageable: Pageable?): Page<Quest>
}