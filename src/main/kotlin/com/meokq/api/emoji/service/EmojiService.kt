package com.meokq.api.emoji.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.AccessDeniedException
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.repository.EmojiRepository
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.response.EmojiCheckResp
import com.meokq.api.emoji.response.EmojiDefaultResp
import com.meokq.api.emoji.response.EmojiResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class EmojiService(
    val repository: EmojiRepository
) :JpaService<Emoji,String>{
    override var jpaRepository: JpaRepository<Emoji, String> = repository

    fun register(authReq: AuthReq ,req: EmojiRegisterReq): EmojiDefaultResp {
        val emoji  = when(req.emojiType.uppercase()){
            "LIKE" -> Emoji(status = EmojiStatus.LIKE)
            "HATE" -> Emoji(status = EmojiStatus.HATE)
            else -> throw IllegalArgumentException("사용할 수 없는 이모지 입니다.")
        }
        if(authReq.userType != UserType.CUSTOMER){
            throw AccessDeniedException("고객만 사용 할 수 있는 기능 입니다.")
        }
        emoji.appendTarget(req,authReq.userId!!)
        return EmojiDefaultResp(saveModel(emoji))
    }

    fun delete(authReq: AuthReq, emojiId: String) {
        val model = findModelById(emojiId)
        if (model.userId != authReq.userId){
            throw AccessDeniedException("해당 이모지를 삭제할 권한이 없습니다.")
        }
        deleteById(emojiId)
    }

    fun countByTarget(targetId :String): EmojiResp {
        val emojis = repository.findByTargetId(targetId = targetId)
        return EmojiResp(emojis)
    }

    fun getEmoji(authReq: AuthReq, targetId :String) : EmojiCheckResp{
        val emojis = repository.findByTargetIdAndUserId(targetId= targetId ,userId = authReq.userId!!)
        return EmojiCheckResp(emojis)
    }

    fun getModels(targetId: String) : MutableList<Emoji> {
        return repository.findByTargetId(targetId)
    }

    fun deleteAllByTargetId(targetId: String) {
        repository.deleteAllByTargetId(targetId)
    }



}