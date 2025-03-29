package com.meokq.api.quest.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.QuestFavorite

interface QuestFavoriteRepository : BaseRepository<QuestFavorite, String> {
    fun findAllByQuestIdInAndCustomerId(
        questIds: List<String>,
        customerId: String
    ): List<QuestFavorite>

    fun deleteByQuestIdAndCustomerId(questId: String, customerId: String)
}
