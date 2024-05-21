package com.meokq.api.emojiHistory.response

import io.swagger.v3.oas.annotations.media.Schema

data class EmojiHistoryResp(
    @Schema(description = "이모지 정보")
    val emojis : List<EmojiResp>
) {
    fun addEmoji(emoji : EmojiResp) {
        emojis.plus(emoji)
    }
}


