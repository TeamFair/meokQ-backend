package com.meokq.api.emoji.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.emoji.annotaions.ExplainDeleteEmoji
import com.meokq.api.emoji.annotaions.ExplainGetEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveEmoji
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.service.EmojiService
import com.meokq.api.xp.annotations.GrantXp
import com.meokq.api.xp.processor.impl.EmojiXpProcessorImpl
import com.meokq.api.xp.processor.impl.EmojiXpReturnProcessorImpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "EmojiHistory", description = "이모지")
@RestController
@RequestMapping("/api")
class EmojiController(
    private val service: EmojiService
): ResponseEntityCreation, AuthDataProvider {

    @ExplainSaveEmoji
    @PostMapping(value =["/customer/emoji"])
    @GrantXp(processor = EmojiXpProcessorImpl::class)
    fun register(@RequestBody request : EmojiRegisterReq): ResponseEntity<BaseResp>  {
        val authReq = getAuthReq()
        return getRespEntity(service.register(authReq,request))
    }


    @ExplainDeleteEmoji
    @DeleteMapping(value =["/customer/emoji"])
    @GrantXp(processor = EmojiXpReturnProcessorImpl::class)
    fun delete(emojiId:String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.delete(authReq,emojiId))
    }

    @ExplainGetEmoji
    @GetMapping(value =["/customer/emoji"])
    fun checkEmoji(challengeId :String): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        return getRespEntity(service.getEmoji(authReq,challengeId))
    }


}