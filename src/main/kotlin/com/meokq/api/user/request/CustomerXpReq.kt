package com.meokq.api.user.request

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

data class CustomerXpReq(
    @NotNull
    val xpPoint : Int
)
