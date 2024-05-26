package com.meokq.api.emoji.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.emoji.annotaions.ExplainDeleteEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveHateEmoji
import com.meokq.api.emoji.annotaions.ExplainSaveLikeEmoji
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
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
    @PostMapping("/emoji/Like")
    fun registerLike(request : EmojiRegisterReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        request.emojiStatus = EmojiStatus.LIKE
        return getRespEntity(service.register(authReq,request ))
    }

    @ExplainSaveHateEmoji
    @PostMapping("/emoji/Hate")
    fun registerHate(request : EmojiRegisterReq): ResponseEntity<BaseResp> {
        val authReq = getAuthReq()
        request.emojiStatus = EmojiStatus.HATE
        return getRespEntity(service.register(authReq,request))
    }

    @ExplainDeleteEmoji
    @PostMapping("/emoji/delete/like")
    fun deleteEmoji(emojiId:String): ResponseEntity<BaseResp> {
        return getRespEntity(service.delete(emojiId))
    }

}