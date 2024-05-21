package com.meokq.api.emojiHistory.controller

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.emojiHistory.annotaions.ExplainSelectCustomer
import com.meokq.api.emojiHistory.annotaions.ExplainUpdateCustomer
import com.meokq.api.emojiHistory.enums.EmojiStatus
import com.meokq.api.emojiHistory.request.EmojiDeleteReq
import com.meokq.api.emojiHistory.request.EmojiReadReq
import com.meokq.api.emojiHistory.request.EmojiRegisterReq
import com.meokq.api.emojiHistory.service.EmojiHistoryService
import com.meokq.api.user.request.CustomerUpdateReq
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "EmojiHistory", description = "좋아요 정보")
@Controller
@RequestMapping("/api")
class EmojiHistoryController(
    private val service: EmojiHistoryService
): ResponseEntityCreation, AuthDataProvider {
    @ExplainSelectCustomer
    @PostMapping("/emoji/Like")
    fun registerLike(questId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val status = EmojiStatus.LIKE
        val request = EmojiRegisterReq(
                        userId = authReq.userId!!,
                        emojiStatus = status,
                        questId = questId)
        return getRespEntity(service.registerEmoji(request))
    }

    @ExplainSelectCustomer
    @PostMapping("/emoji/Hate")
    fun registerHate(questId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val status = EmojiStatus.HATE
        val request = EmojiRegisterReq(
            userId = authReq.userId!!,
            emojiStatus = status,
            questId = questId)
        return getRespEntity(service.registerEmoji(request))
    }

    @ExplainSelectCustomer
    @PostMapping("/emoji/delete/like")
    fun deleteEmoji(emojiId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val request = EmojiDeleteReq(
            userId = authReq.userId!!,
            emojiId = emojiId)
        return getRespEntity(service.deleteEmoji(request))
    }

    @ExplainSelectCustomer
    @PostMapping("/emoji/read")
    fun readEmoji(questId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val request = EmojiReadReq(
            userId = authReq.userId!!,
            questId = questId)
        return getRespEntity(service.readEmoji(request))
    }
}