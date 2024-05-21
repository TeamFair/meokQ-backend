package com.meokq.api.emojiHistory.response

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Emoji
import com.meokq.api.user.model.EmojiHistory

data class EmojiResp(
    val emojis : List<Emoji>,
    val userId : String
    ) {

}