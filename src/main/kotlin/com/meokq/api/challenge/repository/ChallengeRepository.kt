package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface ChallengeRepository : BaseRepository<Challenge, String> {
    //fun findAllByStatus(status: ChallengeStatus): List<Challenge>
    //fun deleteAllByQuestId(questId:String)
    fun findAllByQuestId(questId: String): List<Challenge>
    fun countByCustomerIdAndStatus(customerId: String, status: ChallengeStatus): Long
    @Query("""
        SELECT c
        FROM Challenge AS c
        WHERE (select count(*) FROM Mission AS m WHERE m.questId = c.questId and m.type != 'FREE') = 0 AND c.status = 'APPROVED'
        ORDER BY CASE WHEN c.likeEmojiCnt = 0 THEN 1 ELSE 0 END ASC, c.updateDate DESC
    """)
    fun findAllRandomChallenge(pageable: Pageable): Page<Challenge>
}
