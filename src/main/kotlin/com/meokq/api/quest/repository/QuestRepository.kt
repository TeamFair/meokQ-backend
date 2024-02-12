package com.meokq.api.quest.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.model.Quest

interface QuestRepository : BaseRepository<Quest, String> {
    //fun updateStatusAndExpireDateById(status: QuestStatus, up)
}