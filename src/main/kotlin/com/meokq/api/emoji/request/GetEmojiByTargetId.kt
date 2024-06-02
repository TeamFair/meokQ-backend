package com.meokq.api.emoji.request

import io.swagger.v3.oas.annotations.media.Schema

class GetEmojiByTargetId (
    @Schema(description = "이모지 적용 ID", example = "CS10000001")
    val targetId :String
)