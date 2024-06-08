package com.meokq.api.emoji.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.xp.processor.impl.EmojiXpProcessorImpl
import com.meokq.api.emoji.annotaions.ExplainDeleteEmoji
import com.meokq.api.emoji.annotaions.ExplainGetEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveLikeEmoji
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.service.EmojiService
import com.meokq.api.xp.annotations.GrantXp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "EmojiHistory", description = "이모지")
@RestController
@RequestMapping("/api")
class EmojiController(
    private val service: EmojiService
): ResponseEntityCreation, AuthDataProvider {
    @ExplainSaveLikeEmoji
    @PostMapping("/customer/emoji/like")
    @GrantXp(processor = EmojiXpProcessorImpl::class)
    fun registerLike(request : EmojiRegisterReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.register(authReq,request ))
    }


    @ExplainDeleteEmoji
    @DeleteMapping("/customer/emoji")
    fun deleteEmoji(emojiId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.delete(authReq,emojiId))
    }

    @ExplainGetEmoji
    @GetMapping("/customer/emoji/{challengeId}")
    fun getEmoji(challengeId :String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.getEmoji(authReq,challengeId))
    }


}