package com.meokq.api.quest.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestRepository : BaseRepository<Quest, String> {
    fun findAllByQuestIdNotInAndStatus(questIds: List<String>, status: QuestStatus, pageable: Pageable): Page<Quest>
}