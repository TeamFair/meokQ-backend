package com.meokq.api.emoji.request

import com.meokq.api.emoji.enums.EmojiStatus

class EmojiRegisterReq(
    val customerId:String,
    val emojiStatus: EmojiStatus,
    val challengeId : String)