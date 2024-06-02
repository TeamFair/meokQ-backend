package com.meokq.api.emoji.request

import io.swagger.v3.oas.annotations.media.Schema

class EmojiRegisterReq(
    @Schema(description = "이모지 적용 대상 ID", example = "CH10000001")
    val targetId: String,
    @Schema(description = "이모지 대상 타입", example = "challenge")
    val targetType: String,
    @Schema(description = "이모지 타입", example = "like,hate")
    val emojiType: String
)
