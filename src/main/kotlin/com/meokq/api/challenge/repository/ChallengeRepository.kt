package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.repository.BaseRepository

interface ChallengeRepository : BaseRepository<Challenge, String> {
    fun findAllByStatus(status: ChallengeStatus): List<Challenge>

}