package com.meokq.api.emoji.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.response.EmojiResp
import com.meokq.api.emoji.repository.EmojiRepository
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.request.GetEmojiByTargetId
import com.meokq.api.user.repository.CustomerRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class EmojiService(
    val repository: EmojiRepository,
    val customerService: CustomerRepository
) :JpaService<Emoji,String>{
    override var jpaRepository: JpaRepository<Emoji, String> = repository

    fun register(req: EmojiRegisterReq){
        val emoji  = when(req.emojiStatus){
            EmojiStatus.LIKE -> Emoji(status = EmojiStatus.LIKE)
            EmojiStatus.HATE -> Emoji(status = EmojiStatus.HATE)
            else -> throw InvalidRequestException("등록 되지 않은 Emoji 입니다.")
        }
        saveModel(emoji)
    }

    fun delete(emojiId: String) {
        deleteById(emojiId)
    }

    fun findByEmoji(req : GetEmojiByTargetId): EmojiResp {
        val emojis = repository.findByTargetIdAndUserId(targetId= req.targetId,userId = req.userId)
        //TODO
    }

}