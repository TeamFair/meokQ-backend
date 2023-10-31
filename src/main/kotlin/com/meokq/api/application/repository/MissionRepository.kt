package com.meokq.api.application.repository

import com.meokq.api.application.model.Mission
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MissionRepository : JpaRepository<Mission, UUID> {
}