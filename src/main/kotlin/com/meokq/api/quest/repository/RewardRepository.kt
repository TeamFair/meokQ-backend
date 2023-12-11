package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Reward
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RewardRepository : JpaRepository<Reward, UUID> {
    fun findAllByQuestId(questId: String) : List<Reward>
    fun deleteAllByQuestId(questId: String)
}