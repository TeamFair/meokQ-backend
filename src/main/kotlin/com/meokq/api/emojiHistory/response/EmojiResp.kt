package com.meokq.api.emojiHistory.response

import com.meokq.api.emojiHistory.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiResp(
    @Schema(description = "이모지")
    val emojiName : String,
    @Schema(description = "이모지 id")
    val emojiId: String,
    @Schema(description = "이모지 생성 일시")
    val createDate: String
){
    constructor(emoji : Emoji) : this(
        emojiName = emoji.status.toString(),
        emojiId = emoji.id!!,
        createDate = emoji.createDate.toString()
    )
}

