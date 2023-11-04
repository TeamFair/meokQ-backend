package com.meokq.api.application.repository

import com.meokq.api.application.model.Challenge
import com.meokq.api.application.model.ChallengeId
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeRepository : JpaRepository<Challenge, ChallengeId> {
}