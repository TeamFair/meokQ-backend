package com.meokq.api.user.repository

import com.meokq.api.user.model.Emoji
import org.springframework.data.jpa.repository.JpaRepository

interface EmojiRepository : JpaRepository<Emoji, String> {

    fun findByEmojiHistoryId
}