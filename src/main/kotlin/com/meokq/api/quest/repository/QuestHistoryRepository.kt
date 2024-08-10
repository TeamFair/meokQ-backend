package com.meokq.api.quest.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.QuestHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestHistoryRepository : BaseRepository<QuestHistory, String> {
    fun findByCustomerId(customerId: String, pageable: Pageable): Page<QuestHistory>
    fun deleteAllByQuestId(questId: String)
}