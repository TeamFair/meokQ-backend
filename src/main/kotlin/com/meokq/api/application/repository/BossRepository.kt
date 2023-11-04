package com.meokq.api.application.repository

import com.meokq.api.application.model.Boss
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BossRepository : JpaRepository<Boss, UUID> {
}