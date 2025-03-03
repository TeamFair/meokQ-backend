package com.meokq.api.quiz.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.Mission
import com.meokq.api.quiz.model.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizRepository : JpaRepository<QuizEntity, String> {
    @Query(value = "SELECT q FROM QuizEntity q WHERE q.mission.missionId = :missionId ORDER BY RAND() LIMIT 1")
    fun findRandomByMissionId(missionId: String): QuizEntity?
}
