package com.meokq.api.emoji.repository

import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.request.GetEmojiByTargetId
import org.springframework.data.jpa.repository.JpaRepository

interface EmojiRepository : JpaRepository<Emoji, String> {
    fun findByTargetIdAndUserId(targetId: String, userId :String): MutableList<Emoji>
    fun findByTargetId(targetId: String) : MutableList<Emoji>
    fun deleteAllByTargetId(targetId: String)
    fun findByTargetType(targetType: TargetType) : MutableList<Emoji>
    fun existsByTargetIdAndUserIdAndStatus(targetId: String, userId: String, status: EmojiStatus): Boolean
}