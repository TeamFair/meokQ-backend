package com.meokq.api.emojiHistory.response

import com.meokq.api.emojiHistory.enums.EmojiStatus
import com.meokq.api.emojiHistory.model.EmojiHistory
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiHistoryResp(
    @Schema(description = "이모지 정보")
    val emojis : MutableList<EmojiResp> = mutableListOf(),

    var likeEmojiCnt : Int = 0,
    var hateEmojiCnt : Int = 0
) {
    constructor(emojis: MutableList<EmojiHistory>) : this() {
        return calculateEmoji(emojis)
    }
    private fun calculateEmoji(emojis : MutableList<EmojiHistory>) {
        for (emoji in emojis) {
            if (emoji.emojiStatus == EmojiStatus.LIKE) {
                likeEmojiCnt++
            } else {
                hateEmojiCnt++
            }
        }
    }
}


