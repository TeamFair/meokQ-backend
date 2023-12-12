package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Reward
import org.springframework.data.jpa.repository.JpaRepository

interface RewardRepository : JpaRepository<Reward, String> {
    fun findAllByQuestId(questId: String) : List<Reward>
    fun deleteAllByQuestId(questId: String)
}