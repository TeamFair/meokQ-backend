package com.meokq.api.user.request

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

@Schema(name = "Customer-Request")
data class CustomerReq(
    @NotNull
    val email : String,
)
