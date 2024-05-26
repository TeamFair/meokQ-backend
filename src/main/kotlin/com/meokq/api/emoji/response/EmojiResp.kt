package com.meokq.api.emoji.response

import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.model.Emoji
import io.swagger.v3.oas.annotations.media.Schema

data class EmojiResp(
    var likeEmojiCnt : Int = 0,
    var hateEmojiCnt : Int = 0
){
    constructor(emojis : List<Emoji>) : this (){
        emojis.forEach{
            emoji ->
            when (emoji.status){
               EmojiStatus.LIKE -> likeEmojiCnt++
               EmojiStatus.HATE -> hateEmojiCnt++
                else -> {}
            }
        }
    }
}

