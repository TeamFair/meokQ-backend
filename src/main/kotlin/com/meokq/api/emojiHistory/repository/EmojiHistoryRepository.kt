package com.meokq.api.emojiHistory.repository

import com.meokq.api.emojiHistory.model.EmojiHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmojiHistoryRepository : JpaRepository<EmojiHistory, String> {

    fun findByEmojiId(emojiId :String) : Optional<EmojiHistory>

    fun findByChallengeId(challengeId: String) : MutableList<EmojiHistory>

}