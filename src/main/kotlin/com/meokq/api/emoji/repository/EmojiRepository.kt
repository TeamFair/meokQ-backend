package com.meokq.api.emoji.repository

import com.meokq.api.emoji.model.Emoji
import org.springframework.data.jpa.repository.JpaRepository

interface EmojiRepository : JpaRepository<Emoji, String> {

    fun findByChallengeId(challengeId: String): MutableList<Emoji>

}