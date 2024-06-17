package com.meokq.api.emoji.request

import io.swagger.v3.oas.annotations.media.Schema

class EmojiDeleteReq(
    @Schema(description = "이모지 적용자 ID", example = "CS10000001")
    val customerId:String,
    @Schema(description = "이모지 ID", example = "EJ10000001")
    val emojiId : String
)