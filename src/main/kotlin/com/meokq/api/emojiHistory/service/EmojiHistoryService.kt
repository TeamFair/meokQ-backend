package com.meokq.api.emojiHistory.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.emojiHistory.enums.EmojiStatus
import com.meokq.api.emojiHistory.enums.EmojiStatus.*
import com.meokq.api.emojiHistory.repository.EmojiHistoryRepository
import com.meokq.api.emojiHistory.request.EmojiDeleteReq
import com.meokq.api.emojiHistory.request.EmojiReadReq
import com.meokq.api.emojiHistory.request.EmojiRegisterReq
import com.meokq.api.emojiHistory.response.EmojiResp
import com.meokq.api.user.model.EmojiHistory
import com.meokq.api.user.service.CustomerService
import com.meokq.api.user.service.EmojiService
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
@Transactional
class EmojiHistoryService(
    val repository: EmojiHistoryRepository,
    val emojiService: EmojiService,
    val customerService: CustomerService
) : JpaService<EmojiHistory, String>{
    override var jpaRepository: JpaRepository<EmojiHistory, String> = repository

    fun registerEmoji(req: EmojiRegisterReq){
        customerService.existsById(req.userId)
        val emoji  = when(req.emojiStatus){
                        LIKE -> emojiService.registerLike()
                        HATE -> emojiService.registerHate()
                        else -> throw InvalidRequestException("등록 되지 않은 Emoji 입니다.")
                    }
        val emojiHistory = EmojiHistory(emojiId = emoji.id, customerId = req.userId, questId = req.questId)
        saveModel(emojiHistory)
    }

    fun deleteEmoji(req: EmojiDeleteReq){
        val emojiHistory = repository.findByEmojiId(emojiId = req.emojiId)
                            .orElseThrow{throw InvalidRequestException("등록 되지 않은 EmojiHistory 입니다.")}
        //이모지작성자와 토큰 사용자 비교
        if(!emojiHistory.customerId.equals(req.userId)){
            throw InvalidRequestException("잘못된 접근 입니다.")
        }
        deleteById(emojiHistory.emojiHistoryId!!)
    }

    fun readEmoji(req : EmojiReadReq): EmojiResp{
        val emojiHistory = repository.findByCustomerIdAndQuestId(customerId = req.userId, questId = req.questId)
        requireNotNull(emojiHistory)
        return EmojiResp( emojis = emojiHistory.get(), userId = req.userId)

    }





}