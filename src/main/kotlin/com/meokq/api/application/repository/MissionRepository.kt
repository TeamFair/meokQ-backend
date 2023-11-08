package com.meokq.api.application.repository

import com.meokq.api.application.model.Mission
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<Mission, String> {
    fun findAllByQuestId(questId : String) : List<Mission>
}