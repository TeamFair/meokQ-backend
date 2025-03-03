package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Mission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MissionRepository : JpaRepository<Mission, String> {
    fun findAllByQuestId(questId : String) : MutableList<Mission>
    fun deleteAllByQuestId(questId: String)
    @Query("SELECT m FROM tb_mission m LEFT JOIN FETCH m.quizzes WHERE m IN :missions")
    fun findWithQuizzesByMissionIdIn(missions: List<Mission>): List<Mission>
}
