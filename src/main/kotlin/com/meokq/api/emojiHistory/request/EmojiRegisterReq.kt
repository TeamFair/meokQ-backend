package com.meokq.api.emojiHistory.request

import com.meokq.api.emojiHistory.enums.EmojiStatus

class EmojiRegisterReq(
    val userId:String,
    val emojiStatus: EmojiStatus,
    val questId : String)