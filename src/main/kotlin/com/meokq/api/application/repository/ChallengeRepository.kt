package com.meokq.api.application.repository

import com.meokq.api.application.enums.ChallengeStatus
import com.meokq.api.application.model.Challenge
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeRepository : JpaRepository<Challenge, String> {
    fun findAllByQuestIdAndStatus(marketId: String, status: ChallengeStatus, pageable: Pageable): Page<Challenge>
}