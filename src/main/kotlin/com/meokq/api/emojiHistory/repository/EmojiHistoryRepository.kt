package com.meokq.api.emojiHistory.repository

import com.meokq.api.user.model.EmojiHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmojiHistoryRepository : JpaRepository<EmojiHistory, String> {

    fun findByEmojiId(emojiId :String) : Optional<EmojiHistory>
    fun findByCustomerIdAndQuestId(customerId: String,questId:String) : Optional<List<EmojiHistory>>
}