package com.meokq.api.user.request

import com.meokq.api.xp.model.XpType
import io.swagger.v3.oas.annotations.media.Schema

class RankSearchCondition (
    @Schema(description = "STRENGTH")
    val xpType: XpType,
    val size : Int
)