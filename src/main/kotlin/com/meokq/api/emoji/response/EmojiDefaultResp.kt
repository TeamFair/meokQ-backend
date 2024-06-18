package com.meokq.api.emoji.response

import com.meokq.api.emoji.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Emoji-Response")
class EmojiDefaultResp(
    emoji: Emoji
){
    var emojiId = emoji.emojiId
    var emojiStatus =emoji.status
    var targetId =emoji.targetId
    var targetType =emoji.targetType
}

