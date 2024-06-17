package com.meokq.api.emoji.request

import io.swagger.v3.oas.annotations.media.Schema

class EmojiReadReq(
    @Schema(description = "이모지 적용자 ID", example = "CS10000001")
    val customerId:String,
    @Schema(description = "이모지 찾을 대상 ID", example = "CH10000001")
    val targetId : String)