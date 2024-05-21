package com.meokq.api.emojiHistory.service

import com.meokq.api.core.JpaService
import com.meokq.api.emojiHistory.enums.EmojiStatus
import com.meokq.api.emojiHistory.model.Emoji
import com.meokq.api.user.repository.EmojiRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class EmojiService(
    val repository: EmojiRepository,
) :JpaService<Emoji,String>{
    override var jpaRepository: JpaRepository<Emoji, String> = repository

    fun registerLike(): Emoji {
        val like = Emoji(status = EmojiStatus.LIKE)
        return saveModel(like)
    }

    fun registerHate(): Emoji {
        val hate = Emoji(status = EmojiStatus.HATE)
        return saveModel(hate)
    }

    fun delete(emojiId: String) {
        deleteById(emojiId)
    }

}