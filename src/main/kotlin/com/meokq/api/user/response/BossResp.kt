package com.meokq.api.user.response

import com.meokq.api.user.enums.UserStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Boss-Response")
class BossResp(
    @Schema(name = "상태값")
    val status : UserStatus,

    @Schema(name = "Boss Id")
    val bossId : String?
)