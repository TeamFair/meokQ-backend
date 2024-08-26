package com.meokq.api.emoji.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.AccessDeniedException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.EmojiStatus.*
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.repository.EmojiRepository
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.response.EmojiCheckResp
import com.meokq.api.emoji.response.EmojiDefaultResp
import com.meokq.api.emoji.response.EmojiResp
import com.meokq.api.quest.service.QuestService
import com.meokq.api.rank.ChallengeEmojiRankService
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.service.XpHistoryService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.print.PrintService

@Service
class EmojiService(
    private val repository: EmojiRepository,
    private val challengeService: ChallengeService,
    private val questService: QuestService,
    private val customerService: CustomerService,
    private val xpHistoryService: XpHistoryService,

    ) :JpaService<Emoji,String>{
    override var jpaRepository: JpaRepository<Emoji, String> = repository

    @Transactional
    fun register(authReq: AuthReq, req: EmojiRegisterReq): EmojiDefaultResp {
        val emojiType = EmojiStatus.fromString(req.emojiType.uppercase())
        if(repository.existsByTargetIdAndUserIdAndStatus(req.targetId, authReq.userId!!,emojiType)){
            throw NotUniqueException("이미 등록된 이모지 입니다.")
        }

        val emoji  = when(emojiType){
            LIKE -> Emoji(LIKE, req, authReq.userId)
            HATE -> Emoji(HATE, req, authReq.userId)
            else -> throw IllegalArgumentException("사용할 수 없는 이모지 입니다.")
        }
        val result = saveModel(emoji)

        updateEmojiRank(req)
        gainXp(authReq.userId, result)

        return EmojiDefaultResp(saveModel(emoji))
    }

    private fun gainXp(userId: String, emoji: Emoji) {
        val action = getUserActionByEmojiType(emoji)
        customerService.gainXp(userId, action.xpPoint)

        xpHistoryService.save(
            action, TargetMetadata(
                targetType = TargetType.EMOJI,
                targetId = emoji.emojiId!!,
                userId = userId
            )
        )
    }

    private fun updateEmojiRank(req: EmojiRegisterReq) {
        when (TargetType.fromString(req.targetType.uppercase())) {
            TargetType.CHALLENGE -> {
                challengeService.updateRank(req.targetId)
            }
            TargetType.QUEST -> {
                val quest = questService.findModelById(req.targetId)
            }

            else -> throw IllegalArgumentException("지원되지 않는 대상 타입입니다.")
        }
    }

    @Transactional
    fun delete(authReq: AuthReq, emojiId: String) {
        val model = findModelById(emojiId)
        if (model.userId != authReq.userId){
            throw AccessDeniedException("해당 이모지를 삭제할 권한이 없습니다.")
        }

        val action = getUserActionByEmojiType(model)
        customerService.returnXp(authReq.userId, action.xpPoint)

        deleteById(emojiId)
        val userAction: UserAction
        when(model.status){
            LIKE -> {
                userAction = UserAction.LIKE_CANCEL
            }
            HATE -> {
                userAction = UserAction.HATE_CANCEL
            }
        }

        xpHistoryService.save(userAction,
            TargetMetadata(
                targetType = TargetType.EMOJI,
                targetId = emojiId,
                userId = authReq.userId
            )
        )
    }

    private fun getUserActionByEmojiType(result: Emoji): UserAction {
        return when (result.status) {
                LIKE -> UserAction.LIKE
                HATE -> UserAction.HATE
                else -> throw IllegalArgumentException("사용할 수 없는 이모지 입니다.")
            }
    }

    fun countByTargetId(targetId :String): EmojiResp {
        val emojis = repository.findByTargetId(targetId = targetId)
        return EmojiResp(emojis)
    }

    fun getEmoji(authReq: AuthReq, targetId :String) : EmojiCheckResp{
        val emojis = repository.findByTargetIdAndUserId(targetId= targetId ,userId = authReq.userId!!)
        return EmojiCheckResp(emojis)
    }

    fun findAllByTargetId(targetId: String) : MutableList<Emoji> {
        return repository.findByTargetId(targetId)
    }

    fun deleteAllByTargetId(targetId: String) {
        repository.deleteAllByTargetId(targetId)
    }


}