package com.meokq.api.emoji.request

import com.meokq.api.emoji.enums.EmojiStatus
import io.swagger.v3.oas.annotations.media.Schema

class EmojiRegisterReq(
    @Schema(example = "MK00000001")
    val targetId: String,
    @Schema(example = "challenge")
    val targetType: String
) {
    lateinit var emojiStatus: EmojiStatus
}
