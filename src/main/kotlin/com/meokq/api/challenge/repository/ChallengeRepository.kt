package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ChallengeRepository : BaseRepository<Challenge, String> {
    fun findAllByStatus(status: ChallengeStatus): List<Challenge>
    fun findByStatus(status: ChallengeStatus,pageable: Pageable): Page<Challenge>
    fun deleteAllByQuestId(questId:String)
    fun findAllByQuestId(questId: String): List<Challenge>
    fun countByCustomerIdAndStatus(customerId: String, status: ChallengeStatus): Long

}