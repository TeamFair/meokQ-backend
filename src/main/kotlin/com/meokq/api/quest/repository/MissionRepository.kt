package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Mission
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<Mission, String> {
    fun findAllByQuestId(questId : String) : List<Mission>
}