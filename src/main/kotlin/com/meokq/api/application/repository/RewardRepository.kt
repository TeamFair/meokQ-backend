package com.meokq.api.application.repository

import com.meokq.api.application.model.Reward
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RewardRepository : JpaRepository<Reward, UUID> {
}