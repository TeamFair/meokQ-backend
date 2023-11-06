package com.meokq.api.application.response

import com.meokq.api.application.enums.UserStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Boss-Response")
class BossResponse(
    @Schema(name = "상태값")
    val status : UserStatus,

    @Schema(name = "Boss Id")
    val bossId : String?
)