package com.meokq.api.quest.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.Quest
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestRepository : BaseRepository<Quest, String> {

    @Query("SELECT q FROM Quest q WHERE q.questId NOT IN (:questIds)")
    fun findAllByNotInIds(@Param("questIds") questIds: List<String>): List<Quest>

}