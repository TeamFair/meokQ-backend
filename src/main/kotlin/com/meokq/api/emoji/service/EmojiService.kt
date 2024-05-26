package com.meokq.api.emoji.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.AccessDeniedException
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
    val repository: EmojiRepository
) :JpaService<Emoji,String>{
    override var jpaRepository: JpaRepository<Emoji, String> = repository

    fun register(authReq: AuthReq ,req: EmojiRegisterReq){
        val emoji  = when(req.emojiStatus){
            EmojiStatus.LIKE -> Emoji(status = EmojiStatus.LIKE)
            EmojiStatus.HATE -> Emoji(status = EmojiStatus.HATE)
        }
        if(authReq.userType != UserType.CUSTOMER){
            throw AccessDeniedException("고객만 사용 할 수 있는 기능 입니다.")
        }
        emoji.appendTarget(req,authReq.userId!!)
        saveModel(emoji)
    }

    fun delete(emojiId: String) {
        deleteById(emojiId)
    }

    fun countByTarget(req : GetEmojiByTargetId): EmojiResp {
        val emojis = repository.findByTargetIdAndUserId(targetId= req.targetId,userId = req.userId)
        return EmojiResp(emojis)
    }

}