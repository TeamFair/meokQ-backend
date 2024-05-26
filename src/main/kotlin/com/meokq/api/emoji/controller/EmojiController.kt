package com.meokq.api.emoji.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.emoji.annotaions.ExplainDeleteEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveHateEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveLikeEmoji
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.service.EmojiService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "EmojiHistory", description = "좋아요 정보")
@Controller
@RequestMapping("/api")
class EmojiController(
    private val service: EmojiService
): ResponseEntityCreation, AuthDataProvider {
    @ExplainSaveLikeEmoji
    @PostMapping("/customer/emoji/like")
    fun registerLike(request : EmojiRegisterReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        request.emojiStatus = EmojiStatus.LIKE
        return getRespEntity(service.register(authReq,request ))
    }

    @ExplainSaveHateEmoji
    @PostMapping("/customer/emoji/hate")
    fun registerHate(request : EmojiRegisterReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        request.emojiStatus = EmojiStatus.HATE
        return getRespEntity(service.register(authReq,request))
    }

    @ExplainDeleteEmoji
    @PostMapping("/customer/emoji/delete")
    fun deleteEmoji(emojiId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.delete(authReq,emojiId))
    }

    @ExplainDeleteEmoji
    @GetMapping("/customer/emoji/get")
    fun getEmoji(challengeId :String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.getEmoji(authReq,challengeId))
    }

}