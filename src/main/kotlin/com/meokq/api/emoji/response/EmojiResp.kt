package com.meokq.api.emoji.response

import com.meokq.api.emoji.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiResp(
    @Schema(description = "이모지")
    val emojiStatus : String,
    @Schema(description = "이모지 id")
    val emojiId: String
){
    constructor(emoji : Emoji) : this (
        emojiStatus = emoji.status.toString(),
        emojiId = emoji.id!!
    )
}

