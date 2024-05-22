package com.meokq.api.emojiHistory.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.emojiHistory.annotaions.ExplainDeleteEmoji
import com.meokq.api.emojiHistory.annotaions.ExplainSaveHateEmoji
import com.meokq.api.emojiHistory.annotaions.ExplainSaveLikeEmoji
import com.meokq.api.emojiHistory.enums.EmojiStatus
import com.meokq.api.emojiHistory.request.EmojiDeleteReq
import com.meokq.api.emojiHistory.request.EmojiReadReq
import com.meokq.api.emojiHistory.request.EmojiRegisterReq
import com.meokq.api.emojiHistory.service.EmojiHistoryService
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
    @ExplainSaveLikeEmoji
    @PostMapping("/emoji/Like")
    fun registerLike(challengeId : String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val status = EmojiStatus.LIKE
        val request = EmojiRegisterReq(
                        userId = authReq.userId!!,
                        emojiStatus = status,
                        challengeId = challengeId)
        return getRespEntity(service.registerEmoji(request))
    }

    @ExplainSaveHateEmoji
    @PostMapping("/emoji/Hate")
    fun registerHate(challengeId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val status = EmojiStatus.HATE
        val request = EmojiRegisterReq(
            userId = authReq.userId!!,
            emojiStatus = status,
            challengeId = challengeId)
        return getRespEntity(service.registerEmoji(request))
    }

    @ExplainDeleteEmoji
    @PostMapping("/emoji/delete/like")
    fun deleteEmoji(emojiId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val request = EmojiDeleteReq(
            userId = authReq.userId!!,
            emojiId = emojiId)
        return getRespEntity(service.deleteEmoji(request))
    }

    @ExplainSaveLikeEmoji
    @PostMapping("/emoji/read")
    fun readEmoji(challengeId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        val request = EmojiReadReq(
            userId = authReq.userId!!,
            challengeId = challengeId)
        return getRespEntity(service.countByChallengeId(request))
    }
}