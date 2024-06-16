package com.meokq.api.emoji.response

import com.meokq.api.emoji.model.Emoji

data class EmojiDefaultResp(
    var emojiId : String,
    var emojiStatus : String,
    var targetId :String,
    var targetType : String
){
    constructor(emoji : Emoji) : this (
        emojiId = emoji.emojiId!!,
        emojiStatus = emoji.status.toString(),
        targetId = emoji.targetId!!,
        targetType = emoji.targetType.toString()
    )
}

