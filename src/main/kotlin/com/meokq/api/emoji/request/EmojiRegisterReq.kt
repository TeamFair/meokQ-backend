package com.meokq.api.emoji.request

import com.meokq.api.emoji.enums.EmojiStatus
import io.swagger.v3.oas.annotations.media.Schema

class EmojiRegisterReq(
    @Schema(description = "이모지 적용 대상 ID", example = "CH10000001")
    val targetId: String,
    @Schema(description = "이모지 대상 타입", example = "challenge")
    val targetType: String
) {
    lateinit var emojiStatus: EmojiStatus
}
