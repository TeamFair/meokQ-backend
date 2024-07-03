package com.meokq.api.emoji.response

import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiCheckResp(
    var likeId : String? = null,
    var isLike : Boolean = false,
    var hateId : String? = null,
    var isHate : Boolean = false
){
    constructor(emojis : List<Emoji>) : this (){
        emojis.forEach{
            emoji ->
            when (emoji.status){
               EmojiStatus.LIKE -> {
                   isLike = true
                   likeId = emoji.emojiId
               }
               EmojiStatus.HATE -> {
                   isHate = true
                   hateId = emoji.emojiId
               }
                else -> {}
            }
        }
    }
}

