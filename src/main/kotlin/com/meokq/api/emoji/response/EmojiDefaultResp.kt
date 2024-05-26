package com.meokq.api.emoji.response

import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiDefaultResp(
    var emojiId : String,
    var emojiStatus : String,
    var targetId :String,
    var targetType : String
){
    constructor(emoji : Emoji) : this (
        emojiId = emoji.id!!,
        emojiStatus = emoji.status.toString(),
        targetId = emoji.targetId!!,
        targetType = emoji.targetType.toString()
    )
}

