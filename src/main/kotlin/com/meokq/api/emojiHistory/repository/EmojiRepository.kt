package com.meokq.api.user.repository

import com.meokq.api.emojiHistory.model.Emoji
import org.springframework.data.jpa.repository.JpaRepository

interface EmojiRepository : JpaRepository<Emoji, String> {

}